#!/bin/bash

STQA_MONGO_PASSWORD=$1
AWS_MONGO_HOST=$2
AWS_MONGO_USERNAME=$3
AWS_MONGO_PASSWORD=$4

mongoexport --host hd5qmdb05lx.digital.hbc.com:27018 -u o5a_prev_toggle_service -p ${STQA_MONGO_PASSWORD} --db o5a_prev_toggle_service --collection toggle --type json -o qa_saks.json
mongoexport --host hd5qmdb05lx.digital.hbc.com:27018 -u o5a_prev_toggle_service -p ${STQA_MONGO_PASSWORD} --db o5a_prev_toggle_service --collection toggle_history --type json -o qa_saks_toggle_history.json
mongoexport --host hd5qmdb05lx.digital.hbc.com:27018 -u o5a_prev_toggle_service -p ${STQA_MONGO_PASSWORD} --db o5a_prev_toggle_service --collection toggle_usage --type json -o qa_saks_toggle_usage.json
mongoexport --host hd5qmdb05lx.digital.hbc.com:27018 -u o5a_prev_toggle_service -p ${STQA_MONGO_PASSWORD} --db o5a_prev_toggle_service --collection toggle_counts --type json -o qa_saks_toggle_counts.json

mongoimport --host ${AWS_MONGO_HOST}:27017  --db toggle -u ${AWS_MONGO_USERNAME} -p ${AWS_MONGO_PASSWORD}  --collection toggle --type json --file qa_saks.json --drop
mongoimport --host ${AWS_MONGO_HOST}:27017  --db toggle -u ${AWS_MONGO_USERNAME} -p ${AWS_MONGO_PASSWORD}  --collection toggle_history --type json --file qa_saks_toggle_history.json --drop
mongoimport --host ${AWS_MONGO_HOST}:27017  --db toggle -u ${AWS_MONGO_USERNAME} -p ${AWS_MONGO_PASSWORD}  --collection toggle_usage --type json --file qa_saks_toggle_usage.json --drop
mongoimport --host ${AWS_MONGO_HOST}:27017  --db toggle -u ${AWS_MONGO_USERNAME} -p ${AWS_MONGO_PASSWORD}  --collection toggle_counts --type json --file qa_saks_toggle_counts.json --drop
