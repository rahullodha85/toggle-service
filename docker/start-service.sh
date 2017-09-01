#!/usr/bin/env bash
echo "Starting the toggle-service on port 9000 ..."
docker run -d --name toggle-service -p 9000:9000 hbcdigital/service:toggle-service-0.1
echo "... done."
