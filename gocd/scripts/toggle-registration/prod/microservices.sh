[[ $banner == 'lat' ]] && ms_banner='lt' || ms_banner=$banner
HOST="nginxservices.svc.prod-${ms_banner}.hbc-digital-1.cns.digital.hbc.com"

. common/microservices.sh $HOST