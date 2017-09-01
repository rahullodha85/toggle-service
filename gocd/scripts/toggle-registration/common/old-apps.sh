HOST=$1
[[ -n $HOST ]] || { . common/exit.sh $FAIL_STATUS "" "[ERROR] HOST not specified"; }

. common/_fetch-toggles-json.sh

# Toggle Registration
echo "Toggle registration..."

toggle_registration_url="http://${HOST}/v1/toggle-service/registrations"

echo "registration url: ${toggle_registration_url}"

/usr/bin/curl -XPOST -H "Content-Type: application/json" $toggle_registration_url -d @toggles.json -o toggle-registration-response.out -w httpcode=%{http_code} | tee output.log | [[ ! -z `grep "httpcode=200"` ]] && echo "Toggle Registration done!" || { . common/exit.sh $FAIL_STATUS "" "[ERROR] Toggle Registration failed: $(cat output.log)"; }
