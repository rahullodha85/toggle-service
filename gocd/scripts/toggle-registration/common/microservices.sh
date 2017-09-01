HOST=$1
[[ -n $HOST ]] || { . common/exit.sh $FAIL_STATUS "" "[ERROR] HOST not specified"; }

# Fetch toggles.json
. common/_fetch-toggles-json.sh

# Toggle Registration
echo "Toggle registration... -> http://${HOST}/v1/toggle-service/registrations"

/usr/bin/curl -XPOST -H "Content-Type: application/json" "http://${HOST}/v1/toggle-service/registrations" -d @toggles.json -o toggle-registration-response.out -w httpcode=%{http_code} | tee output.log | [[ ! -z `grep "httpcode=200"` ]] && echo "Toggle Registration done!" || { . common/exit.sh $FAIL_STATUS "" "[ERROR] Toggle Registration failed: $(cat output.log)"; }
