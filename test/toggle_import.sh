# Script exports toggles JSON from NGINX End point and imports to AWS Toggle Service Mongo

#!/bin/bash

#./initvars.sh  --use if passing variables in a separate variable initialization file.

export banner="saks"
export env="stqa"
export api_url="http://nginxservices.svc.qa-s5a.hbc-digital-1.cns.digital.hbc.com/v1"
export aws_url="ec2-user@ec2-34-207-242-210.compute-1.amazonaws.com"
export aws_mongo_env="192.168.2.59:27017"
export aws_login="--username test --password pwd"
export mongo_db_conn="toggle_test"
export pairing_key="pairing_two.pem"

echo "banner"    $banner
echo "env"       $env
echo "aws url"   $aws_url
echo "aws mongo" $aws_mongo_env
echo "mongo db"  $mongo_db_conn


export toggle_json_file="${banner}_toggle_extract_${env_from}.json"
export toggle_json_file_new="${banner}_toggle_extract_${env_from}_new.json"

#Extracting Data from NGINX End point
curl ${api_url}/toggle-service/toggles > ${toggle_json_file}

#Sed to format JSON to be able to pass as an Array
sed -e '1,/\[/d' "${toggle_json_file}"|sed 's/\(.*\)"errors".*/\1/'|tac | sed -e '1,/\]/d' | tac > "${toggle_json_file_new}"
sed -i '1s/^/[{\n/' "${toggle_json_file_new}"| echo "}]" >> "${toggle_json_file_new}"

#Copy Toggle Extract JSON file to AWSInstance
scp -i "${pairing_key}" "${toggle_json_file_new}" ${aws_url}:${toggle_json_file_new}

#SSH to AWS Instance
ssh -t -t -i "pairing_two.pem" $aws_url <<EOF
mongoimport --host $aws_mongo_env $aws_login  --db $mongo_db_conn  --collection toggles --type json --file ${toggle_json_file_new} --jsonArray --drop
exit
EOF
