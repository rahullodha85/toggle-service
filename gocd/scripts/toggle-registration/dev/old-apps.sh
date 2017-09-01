SLOTNUMBER=$(printf "%02g" $(echo ${slotnumber} | grep -o -E '[0-9]+'))
echo "environment set is... ${ENV}"
SLOT_TYPE=$( [[ ${ENV} == 'ltqa' ]] && echo 'q' || echo 'd')
HOST="hd5${SLOT_TYPE}dkr${SLOTNUMBER}lx.digital.hbc.com:9860"

. common/old-apps.sh $HOST
