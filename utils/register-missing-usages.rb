# This utility script automates registering usages for all apps (that we know use toggles)
# on multiple slots or on upper environments, at once.
#
# To run this you need to assign 
# - GITHUB_TOKEN
# - $working_slots variable to whatever slots you want to run this for.
# - comment/uncomment `main_slots` or `main_upper` in the last line,
#   according to which environments you want to register
#
# Notes:
# 1) if a slot has usages for unexpected apps a ERROR is raised and you should update the list $apps_that_use_toggles
#    so that next run will register those apps too
# 2) we know that some apps are not deployed in LT environment: 
#    use `exclusion_list_for_lt` subtracting it to $apps_that_use_toggles, in order to avoid extra warnings

require 'net/http'
require 'json'
require 'rest-client'

exclusion_list_for_lt = ['contact_center','endeca','account-service','customer-notification-service','favorite-service','find-in-store-service','omsinventory-service','payment-method-service','sendorderemail-service','user-account-service','user-authentication-service','order-service']
$apps_that_use_toggles = ['account-service','auth_batch_processor','alfresco','color-swatch-service','contact_center','customer-notification-service','e4x_batch_processor','endeca','favorite-service','find-in-store-service','omsinventory-service','order-service','payment-method-service','price-service','product-detail-service','saks_mobile','saks_website','saks_website_backend','sendorder_o5a','sendorder_s5a','sendorderemail-service','user-authentication-service']

GITHUB_TOKEN= ''
broken_slots = [1,3,4,6, (11..29).to_a, 31,33,37,38,39].flatten
$working_slots = (3..10).to_a  - broken_slots
$envs_missing_usages = Hash.new
$extra_toggles = []
$overall_missing_toggles = Hash.new

def find_missing_usages()
    $overall_missing_toggles = Hash.new

    # Check toggle usages
    $working_slots.map { |s| "%02d" % s}.map { |slot|

        uri = URI "http://hd5qdkr#{slot}lx.digital.hbc.com:9860/v1/toggle-service/usages"

        http = Net::HTTP.new(uri.host, uri.port)
        http.read_timeout = 5
        http.open_timeout = 5
        begin
            response = http.start() { |http| http.get(uri.path) }
        rescue Exception => e
            puts "Slot #{slot} is broken: #{e}"
            next
        end

        puts "Slot #{slot}..."
         raw_response = Net::HTTP.get_response uri
         usages_response = JSON.parse raw_response.body

        actual_usages = usages_response['response']['results'].map { |t| t['user'] }.uniq

        missing = $envs_missing_usages["#{slot}"] = $apps_that_use_toggles - actual_usages
        $extra_toggles.concat actual_usages - $apps_that_use_toggles

        $overall_missing_toggles["#{slot}"] = missing if missing.any?

        puts "Missing toggles: #{missing}"
    }
end

def find_missing_usages_UPPER()
    $overall_missing_toggles = Hash.new

    envs = ['qa','preview', 'prod']
    banners = ['lt']

    # Check toggle usages
    envs.map { |env| banners.map { |banner|
        uri = URI "http://nginxservices.svc.#{env}-#{banner}.hbc-digital-1.cns.digital.hbc.com/v1/toggle-service/usages"

        http = Net::HTTP.new(uri.host, uri.port)
        http.read_timeout = 5
        http.open_timeout = 5
        begin
            response = http.start() { |http| http.get(uri.path) }
        rescue Exception => e
            puts "\n#{env}.#{banner} is broken: #{e}"
            next
        end

        puts "\n#{env} #{banner}..."
         raw_response = Net::HTTP.get_response uri
         usages_response = JSON.parse raw_response.body

        actual_usages = usages_response['response']['results'].map { |t| t['user'] }.uniq

        missing = $envs_missing_usages["#{env}-#{banner}"] = $apps_that_use_toggles - actual_usages
        $overall_missing_toggles["#{env}-#{banner}"] = missing if missing.any?
        $extra_toggles.concat actual_usages - $apps_that_use_toggles

        puts "Missing toggles: #{missing}"
        puts "Extra toggles: #{actual_usages - $apps_that_use_toggles}"
    }}
end

def check_extra_toggles
    if $extra_toggles.any?
        puts "ERROR: fix Extra toggles #{$extra_toggles}"
        exit 1
    end
    puts "Extra toggles: #{$extra_toggles}"
end

# Register
def register_missing_usages()

    request_headers = {
                        :Authorization => "token #{GITHUB_TOKEN}",
                        :Accept => 'application/vnd.github.v3.raw'
                      }

    auth_batch_processor_json = '{
      "item": {
        "user": "auth_batch_processor",
        "toggles": [
          {
            "toggle_name": "REALTIME_REVERSAL",
            "description": "",
            "toggle_type": " "
          },
          {
            "toggle_name": "SEND_ORDER_PAYMENT_REFACTOR",
            "description": "",
            "toggle_type": ""
          }
        ]
      }
    }'

    $envs_missing_usages.map { |slot, missing_usages|
        missing_usages.map { |repo_name|
            puts repo_name

            case repo_name
            when 'auth_batch_processor'
                begin
                    RestClient.post(
                        "http://hd5qdkr#{slot}lx.digital.hbc.com:9860/v1/toggle-service/registrations",
                        auth_batch_processor_json,
                        {:content_type => 'application/json', :timeout => 30}
                    )
                rescue Exception => e
                    puts "[ERROR] Couldn't register #{slot} #{repo_name} - #{e}"
                end
                next
            when 'sendorder_s5a'
                repo_name = 'sendorder'
                branch = 'branch_from_trunk_989'
            when 'sendorder_o5a'
                repo_name = 'sendorder'
                branch = 'branch_from_trunk_982'
            else
                branch = 'master'
            end

            begin
                toggles_json = RestClient.get "https://raw.githubusercontent.com/saksdirect/#{repo_name}/#{branch}/toggles.json", request_headers
            rescue Exception => e
                puts "[ERROR] Couldn't fetch toggles.json for #{repo_name} - #{e}"
                next
            end

            puts "Toggles that will be registered for slot #{slot} and repo #{repo_name}:\n #{toggles_json}"
            RestClient.log = "register-missing-usages.log"
            begin
                RestClient.post(
                    "http://hd5qdkr#{slot}lx.digital.hbc.com:9860/v1/toggle-service/registrations",
                    toggles_json.body,
                    {
                        :content_type => 'application/json',
                        :timeout => 30
                    }
                )
            rescue Exception => e
                puts "[ERROR] Couldn't register #{slot} #{repo_name} - #{e}"
            end
        }
    }
end


def register_missing_usages_UPPER()

    request_headers = {
                        :Authorization => "token #{GITHUB_TOKEN}",
                        :Accept => 'application/vnd.github.v3.raw'
                      }

    auth_batch_processor_json = '{
      "item": {
        "user": "auth_batch_processor",
        "toggles": [
          {
            "toggle_name": "REALTIME_REVERSAL",
            "description": "",
            "toggle_type": " "
          },
          {
            "toggle_name": "SEND_ORDER_PAYMENT_REFACTOR",
            "description": "",
            "toggle_type": ""
          }
        ]
      }
    }'

    $envs_missing_usages.map { |env_banner, missing_usages|
        missing_usages.map { |repo_name|
            puts repo_name
            puts "http://nginxservices.svc.#{env_banner}.hbc-digital-1.cns.digital.hbc.com/v1/toggle-service/registrations"

            case repo_name
            when 'auth_batch_processor'
                begin
                    RestClient.post(
                        "http://nginxservices.svc.#{env_banner}.hbc-digital-1.cns.digital.hbc.com/v1/toggle-service/registrations",
                        auth_batch_processor_json,
                        {:content_type => 'application/json', :timeout => 30}
                    )
                rescue Exception => e
                    puts "[ERROR] Couldn't register #{env_banner} #{repo_name} - #{e}"
                end
                next
            when 'sendorder_s5a'
                repo_name = 'sendorder'
                branch = 'branch_from_trunk_989'
            when 'sendorder_o5a'
                repo_name = 'sendorder'
                branch = 'branch_from_trunk_982'
            else
                branch = 'master'
            end

            begin
                toggles_json = RestClient.get "https://raw.githubusercontent.com/saksdirect/#{repo_name}/#{branch}/toggles.json", request_headers
            rescue Exception => e
                puts "[ERROR] Couldn't fetch toggles.json for #{repo_name} - #{e}"
                next
            end

            puts "Toggles that will be registered for #{env_banner} and repo #{repo_name}:\n #{toggles_json}"
            RestClient.log = "register-missing-usages.log"
            begin
                RestClient.post(
                    "http://nginxservices.svc.#{env_banner}.hbc-digital-1.cns.digital.hbc.com/v1/toggle-service/registrations",
                    toggles_json.body,
                    {
                        :content_type => 'application/json',
                        :timeout => 30
                    }
                )
            rescue Exception => e
                puts "[ERROR] Couldn't register #{env_banner} #{repo_name} - #{e}"
            end
        }
    }
end



# MAIN
def main_slots()
    find_missing_usages
    check_extra_toggles

    puts "Toggles that need to be registered: #{$envs_missing_usages}"

    register_missing_usages

    # Re-run check to verify no more missing usages
    find_missing_usages
    if $overall_missing_toggles.any?
        puts "ERROR: there are still missing toggles #{$overall_missing_toggles}"
        exit 1
    end
end

def main_upper()
    find_missing_usages_UPPER
    # check_extra_toggles

    register_missing_usages_UPPER

    find_missing_usages_UPPER
    if $overall_missing_toggles.any?
        puts "ERROR: there are still missing toggles"
        exit 1
    end
end

main_slots
# main_upper
