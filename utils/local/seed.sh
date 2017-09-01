#!/bin/bash

mongoexport --db $DB_NAME --host $FROM_HOST --collection toggle --username $MONGO_USER --password $MONGO_PASSWORD > toggles_dump.json

mongoimport --db toggle_service --host localhost:27017 --username dev --password dev321 --collection toggle --file toggles_dump.json

