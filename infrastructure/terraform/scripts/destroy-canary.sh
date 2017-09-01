#!/usr/bin/env bash

set -ex

tag=$1

# Toggle-Service deploy
cd ./toggles/canary
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform apply \
      -var image=hbc-docker.jfrog.io/toggle-service:$tag \
      -var NEW_RELIC_LICENSE_KEY=${NEW_RELIC_LICENSE_KEY} \
      -var NEW_RELIC_APP_NAME=${NEW_RELIC_APP_NAME} \
      -var PROD_TF_ENV=${PROD_TF_ENV} \
      -var component=canary \
      -var asg_min=0 \
      -var asg_max=0 \
      -var asg_desired=0