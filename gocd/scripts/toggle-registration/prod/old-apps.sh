case $GO_ENVIRONMENT_NAME in
    "Saks_Off5th") website_banner=o5a;;
    "Saks_Off5th_Prod") website_banner=o5a;;
    "Production") website_banner=s5a ;;
    "Lord_Taylor_Prod") website_banner=lt ;;
    *) . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid banner GO_ENVIRONMENT_NAME='${GO_ENVIRONMENT_NAME}'";;
esac

HOST="nginxservices.svc.prod-${website_banner}.hbc-digital-1.cns.digital.hbc.com"

. common/old-apps.sh $HOST
