[[ -n $BUILD_TASK_NAME ]] || { . common/exit.sh $FAIL_STATUS "" "[ERROR] BUILD_TASK_NAME not specified"; }

BUILD_JOB_PATH=$(env | grep "GO_DEPENDENCY_LOCATOR_*" | cut -d= -f2)
TOGGLES_JSON_URL="$GOCD_HOST/go/files/${BUILD_JOB_PATH}/${BUILD_TASK_NAME}/artifacts/toggles.json"

echo "Fetch artifact: http://${TOGGLES_JSON_URL}"
/usr/bin/curl "http://${GO_AUTH}@${TOGGLES_JSON_URL}" -o toggles.json -w httpcode="%{http_code}" | tee output.log | [[ ! -z `grep "httpcode=200"` ]] || { . common/exit.sh $FAIL_STATUS "" "[ERROR] Unable to fetch artifact toggles.json\n$(cat output.log)"; }

# Failsafe-Skip registration if toggles.json is empty
[[ -s toggles.json ]] || { echo "[WARN] toggles.json is empty, skipped toggle registration"; exit 0; }
