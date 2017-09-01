#!/usr/bin/env python2

import json
import sys
import requests
import os
from rules import ToggleRegistrationRule, ToggleSearcher, ToggleExpirationRule, ToggleCleanupRule, ToggleCapitalizationRule, ToggleDefinitionValidityRule
import subprocess

TEMP_EXPIRATION = 180
TOGGLE_URL = "http://nginxservices.svc.prod-s5a.hbc-digital-1.cns.digital.hbc.com/v1/toggle-service/toggles"

def all_passed(rules):
    return all(map(lambda rule: rule.is_passed(), rules))

def notify_slack(message):
    go_hostname = "go.digital.hbc.com" if os.path.expandvars("${GO_PIPELINE_NAME}").endswith("-service") else "go.saksdirect.com"

    bashCommand = os.path.expandvars("curl -X POST -H 'Content-Type: application/json' --data '{\"channel\": \"#toggle-validation\", \"username\": \"Toggle Validation\", \"text\": \"%s for ${GO_PIPELINE_NAME} (#${GO_PIPELINE_LABEL}) - check <http://%s\/go\/files\/${GO_PIPELINE_NAME}\/${GO_PIPELINE_COUNTER}\/${GO_STAGE_NAME}\/${GO_STAGE_COUNTER}\/${GO_JOB_NAME}/cruise-output/console.log|console output> logs\", \"icon_emoji\": \":ghost:\"} ' https://hooks.slack.com/services/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 || echo 'Unable to post result to slack.'" % (message, go_hostname))

    subprocess.Popen(bashCommand, shell=True, stdout=subprocess.PIPE).communicate()

def fail():
    print("--- Toggle validation failure ---")
    print("To fix validation failures, see: http://docs.digital.hbc.com/index.php?title=Toggles_Registration_Automation#Solving_validation_failures")
    notify_slack(":no_entry: FAILED")
    sys.exit(-1)

def create_if_missing(fname):
    if not os.path.exists(fname):
        open(fname, 'a').close()

def main():
    validation_success = False
    try:
        if not os.path.isfile('toggles.json'):
            print("--- No toggle.json file---")
            create_if_missing('toggles.json')
            print("--- Created empty toggles.json file - Skipping Toggle validation ---")
            notify_slack(":warning: SKIPPED toggles.json missing")
            sys.exit(0)

        with open('toggles.json') as json_data:
            my_toggles = json.load(json_data)['item']['toggles']

        toggle_searcher = ToggleSearcher()
        reference_toggles = requests.get(TOGGLE_URL).json()['response']['results']

        rules = [
            ToggleDefinitionValidityRule(my_toggles),
            ToggleRegistrationRule(my_toggles, reference_toggles, toggle_searcher),
            ToggleCleanupRule(my_toggles, toggle_searcher),
            ToggleCapitalizationRule(my_toggles, exceptions = ['sale_page_with_1920', 'FF Invitations'])
        ]
        failsafe_rules = [ToggleExpirationRule(my_toggles, reference_toggles)]

        [ rule.evaluate() for rule in failsafe_rules ]
        [ rule.evaluate() for rule in rules ]

        if all_passed(rules):
            validation_success = True
            print"--- Toggle validation success ---"
            notify_slack(":white_check_mark: PASSED!")

    except ValueError:
        print("--- Malformed JSON in toggle.json file ---")
    except Exception as e:
        print e

    if not validation_success:
        fail()


if __name__ == '__main__':
    main()
    sys.exit(0)
