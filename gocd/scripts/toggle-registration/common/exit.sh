#!/bin/bash
EXIT_STATUS=$1
MSG="$2"
ERROR="$3"

if [ "$TEST" != "true" ]; then
  if [[ -z $ERROR ]]; then
    emoji=":registered:"
    result="Succeeded! Toggles registered"
  else
    emoji=":red_circle:"
    result="FAILED toggle-registration"
    ERROR_MSG="\`\`\`${ERROR}\`\`\`"
    MSG=$ERROR
  fi

  ENVIRONMENT=$ENV
  case $ENV in
    dev|ltqa        ) ENVIRONMENT="${ENV} ${slotnumber}${DOCKER_INSTANCE}" ;;
    qa|preview|prod ) ENVIRONMENT="${ENV} ${banner}" ;;
  esac

  PIPELINE_URL="http:\/\/${GOCD_HOST}\/go\/files\/${GO_PIPELINE_NAME}\/${GO_PIPELINE_COUNTER}\/${GO_STAGE_NAME}/${GO_STAGE_COUNTER}\/${GO_JOB_NAME}\/cruise-output\/console.log"

  # Slack Notification
  /usr/bin/curl -X POST -H 'Content-Type: application/json' --data "{\"channel\": \"#toggle-registration\", \"username\": \"Toggle Registration\", \"text\": \"${emoji} ${result} for *${APPNAME}* (#${GO_PIPELINE_LABEL}) on *${ENVIRONMENT}* - check <${PIPELINE_URL}|${GO_PIPELINE_NAME} console output logs>${ERROR_MSG}\", \"icon_emoji\": \":registered:\"}" https://hooks.slack.com/services/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 || echo 'Unable to post result to slack.'
fi

printf "\n${MSG}\n"
exit $EXIT_STATUS
