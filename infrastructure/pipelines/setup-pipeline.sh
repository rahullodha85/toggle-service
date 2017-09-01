#!/usr/bin/env bash

set -ex

BRANCH_NAME=$(git rev-parse --abbrev-ref HEAD)

fly set-pipeline \
    -t $TARGET -p toggle-service_$BRANCH_NAME \
    -c ./infrastructure/pipelines/pipeline.yml \
    --load-vars-from ./infrastructure/pipelines/config.yml \
    --load-vars-from $SECRETS_FILE \
    -v app-branch=$BRANCH_NAME

fly pause-job -t $TARGET -j toggle-service_$BRANCH_NAME/test

fly unpause-pipeline -t $TARGET -p toggle-service_$BRANCH_NAME


echo "Done!"