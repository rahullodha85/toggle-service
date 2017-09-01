HOST=$1
[[ -n $HOST ]] || { . common/exit.sh $FAIL_STATUS "" "[ERROR] HOST not specified"; }

. common/_fetch-toggles-json.sh

# Toggle Registration
echo "Toggle registration... -> http://${HOST}/v1/toggle-service/registrations"

toggle_registration_url="http://${HOST}/v1/toggle-service/registrations"

echo "registration url: ${toggle_registration_url}"

/usr/bin/curl -XPOST -H "Content-Type: application/json" $toggle_registration_url -d @toggles.json -o toggle-registration-response.out -w httpcode=%{http_code} | tee output.log | [[ ! -z `grep "httpcode=200"` ]] && echo "Toggle Registration done!" || { . common/exit.sh $FAIL_STATUS "" "[ERROR] Toggle Registration failed: $(cat output.log)"; }


# dependencies toggle registration

unzip_jar_and_register_toggles() {
  local JAR_NAME=$1

  echo "${JAR_NAME} - Toggle registration..."

  unzip -p ../../../dist/appserver/website/classes/$JAR_NAME-*.jar toggles.json 2> /dev/null > $JAR_NAME-toggles.json \
  || { . common/exit.sh $FAIL_STATUS "" "[ERROR] unable to find toggles.json in ${JAR_NAME} jar"; }

  [[ -e $JAR_NAME-toggles.json ]] && [[ -s $JAR_NAME-toggles.json ]] && \
   (/usr/bin/curl -XPOST -H "Content-Type: application/json" $toggle_registration_url -d @$JAR_NAME-toggles.json -o toggle-registration-response.out -w httpcode=%{http_code} | tee output.log | [[ ! -z `grep "httpcode=200"` ]] && echo "${JAR_NAME} - Toggle registration successful" ) || { . common/exit.sh $FAIL_STATUS "" "[ERROR] ${JAR_NAME}-toggles.json file missing or empty: $(cat output.log)"; }
}

if [[ $APPNAME == 'website' ]]; then
	unzip_jar_and_register_toggles 'saks-alfresco'
	unzip_jar_and_register_toggles 'website-backend'
fi
