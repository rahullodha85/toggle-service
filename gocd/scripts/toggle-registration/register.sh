#!/bin/bash
ENV=""
APPNAME=""
FAIL_STATUS=142

echo "Toggle registration..."

set -e

while [ "$1" != "" ]; do
  case $1 in
    --env)
      ENV=$2
      ;;
    --appname)
      APPNAME=$2
      ;;
    --errorcode)
      FAIL_STATUS=$2
      ;;
    *)
      ;;
  esac
  shift
done

[[ $FAIL_STATUS == 0 ]] && echo "Failsafe mode ON"

echo "ENV = $ENV"

# QA == STQA
[[ ${ENV} == qa ]]  && echo "translating ENV qa => stqa" && export ENV=stqa

# Slot refresh: parse slottype and override env (some pipelines use "qa" instead of "ltqa")
[[ ${slottype} == dev ]] && echo "slottype overriding ENV => dev"  && export ENV=dev
[[ ${slottype} == qa ]]  && echo "slottype overriding ENV => ltqa" && export ENV=ltqa

# Parse appname
echo "APPNAME = $APPNAME"
GOCD_HOST="go.saksdirect.com"
SCRIPT_FILENAME=""

case $APPNAME in
  hbc-toggle-service)
    # Skip
    echo "INFO: Toggle registration skipped, toggle-service doesn't need it"
    exit 0
    ;;
  *-service)
    GOCD_HOST="go.digital.hbc.com"
    export BUILD_TASK_NAME=build_app
    SCRIPT_FILENAME="microservices.sh"
    ;;
  website|mobile)
    export APPNAME=$APPNAME
    export BUILD_TASK_NAME=ci
    SCRIPT_FILENAME="website-mobile.sh"
    ;;
  e4x-batch-processor)
    export BUILD_TASK_NAME=deploy_ci
    SCRIPT_FILENAME="old-apps.sh"
    ;;
  contact-center|auth-batch-processor|send-order|endeca)
    export BUILD_TASK_NAME=ci
    SCRIPT_FILENAME="old-apps.sh"
    ;;
  *)
    . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid appname '${APPNAME}'"
    ;;
esac

[[ ! -n $GO_AUTH ]] && { . common/exit.sh $FAIL_STATUS "" "[ERROR] GO_AUTH environment variable not set"; }

# Validate env
for e in dev ltqa stqa preview prod; do
  [[ $ENV == $e ]] && valid_env=true
done

[[ ! -n $valid_env ]] && { . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid env '$ENV'"; }

export ENV=$ENV
export GOCD_HOST
export FAIL_STATUS=$FAIL_STATUS
$ENV/$SCRIPT_FILENAME && . common/exit.sh 0 "Done"
