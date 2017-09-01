ST=$(hostname --ip-address)
PORT=9860
curl -X DELETE http://$CONSUL_HOST/v1/kv/$ENV\_toggle\_upstream/$HOST
