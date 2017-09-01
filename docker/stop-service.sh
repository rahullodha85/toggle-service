#!/usr/bin/env bash
echo "Stopping the toggle-service ..."
docker stop toggle-service; docker rm toggle-service
echo "... done."
