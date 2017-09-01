#!/usr/bin/env bash
set -ex
tag=$1

#EBS Deploy
export v_count=1

export volume_id=$(aws ec2 describe-volumes --filters Name=tag-key,Values="Name" Name=tag-value,Values="PrimaryMongo-toggle_service-${TF_ENV}" --query Volumes[].VolumeId --output text)
if [ $volume_id ]; then
    export v_count=0;
fi

# Mongo EBS deploy
cd mongo/ebs
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform apply -var v_count=${v_count}

# Mongo deploy
cd ..
if [ ! -f mongo-key ] ; then
    openssl rand -base64 756 > mongo-key
    chmod 400 mongo-key
fi
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform apply

# Toggle-Service deploy
cd ../toggles
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform apply \
      -var image=hbc-docker.jfrog.io/toggle-service:$tag \
      -var NEW_RELIC_LICENSE_KEY=${NEW_RELIC_LICENSE_KEY} \
      -var NEW_RELIC_APP_NAME=${NEW_RELIC_APP_NAME}