# Map toggle-service host to banner specific enviroment

case $GO_ENVIRONMENT_NAME in
    "Saks_Off5th") HOST="nginxservices.svc.qa-o5a.hbc-digital-1.cns.digital.hbc.com" ;;
    "Short_Term_QA") HOST="nginxservices.svc.qa-s5a.hbc-digital-1.cns.digital.hbc.com" ;;
    "Lord_Taylor_QA") HOST="nginxservices.svc.qa-lt.hbc-digital-1.cns.digital.hbc.com" ;;
    *) . common/exit.sh $FAIL_STATUS "" "[ERROR] Invalid banner GO_ENVIRONMENT_NAME='${GO_ENVIRONMENT_NAME}'";;
esac

. common/website-mobile.sh $HOST
