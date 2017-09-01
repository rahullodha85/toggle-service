#!/bin/bash

export DB_NAME=toggle_service
export DB_USERNAME=dev
export DB_PASSWORD=dev321
export DB_PORT=27017
export DB_HOST=127.0.0.1

docker-compose up -d

sleep 1 # lazy workaround to make sure mongod is started up before executing the next command

echo "db.createUser({ user: '${DB_USERNAME}', pwd: '${DB_PASSWORD}', roles: [ { role: 'userAdminAnyDatabase', db: 'admin' }, 'readWrite' ] });" | mongo "${DB_HOST}:${DB_PORT}/${DB_NAME}"
