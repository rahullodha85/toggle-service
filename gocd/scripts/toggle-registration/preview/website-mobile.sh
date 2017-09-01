case $GO_ENVIRONMENT_NAME in
    "Saks_Off5th") website_banner=o5a;;
    "Preview") website_banner=s5a ;;
    "Lord_Taylor_Preview") website_banner=lt ;;
    *) . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid banner GO_ENVIRONMENT_NAME='${GO_ENVIRONMENT_NAME}'";;
esac

HOST="nginxservices.svc.preview-${website_banner}.hbc-digital-1.cns.digital.hbc.com"

. common/website-mobile.sh $HOST
