case $GO_ENVIRONMENT_NAME in
    "Saks_Off5th") website_banner=o5a;;
    "Short_Term_QA") website_banner=s5a ;;
    "Lord_Taylor_QA") website_banner=lt ;;
    *) . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid banner GO_ENVIRONMENT_NAME='${GO_ENVIRONMENT_NAME}'";;
esac

HOST="nginxservices.svc.qa-${website_banner}.hbc-digital-1.cns.digital.hbc.com"

. common/old-apps.sh $HOST
