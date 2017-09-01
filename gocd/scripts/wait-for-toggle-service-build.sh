printf "Waiting for hbc-toggle-service build"
pipeline=hbc-toggle-service
schedulable=

curl -s "http://go.digital.hbc.com/go/api/pipelines/${pipeline}/status" -XGET -u "${GO_AUTH}" | [[ ! -z `grep "\"schedulable\":true"` ]] && schedulable="true"

TIMEOUT=480
time=0

while [[ ! -n $schedulable ]] && [[ time -lt $TIMEOUT ]] ; do
  printf "."
  sleep 1s
  time=$((time+1))
  curl -s "http://go.digital.hbc.com/go/api/pipelines/${pipeline}/status" -XGET -u "${GO_AUTH}" | [[ ! -z `grep "\"schedulable\":true"` ]] && schedulable="true"
done

if [[ ! -n $schedulable ]]; then
  printf "\nTimeout!"
  exit 1
else
  printf "\nDone"
fi
