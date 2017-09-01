#!/usr/bin/env bash

set -ex

# Destroy Toggle Service
cd toggles
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform destroy -force \
      -var image=hbc-docker.jfrog.io/toggle-service:v3 \
      -var NEW_RELIC_LICENSE_KEY="foo" \
      -var NEW_RELIC_APP_NAME="bar"
rm -rf ./.terraform
rm -rf ./*.tfstate*

# Destroy Mongo Instance
cd ../mongo
terraform init
terraform env new ${TF_ENV} &>/dev/null || terraform env select ${TF_ENV}
terraform destroy -force
rm -rf ./.terraform
rm -rf ./*.tfstate*
rm -f mongo-key