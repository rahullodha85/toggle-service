#!/bin/sh
HOST=$(hostname --ip-address)
PORT=9860
consul-template -consul $CONSUL_HOST -template "/opt/toggle-service-0.1/conf/toggle-application.ctmpl:/opt/toggle-service-0.1/conf/toggle-application.conf" -once;
consul-template -consul $CONSUL_HOST -template "/opt/newrelic/newrelic.ctmpl:/opt/newrelic/newrelic.yml" -once
