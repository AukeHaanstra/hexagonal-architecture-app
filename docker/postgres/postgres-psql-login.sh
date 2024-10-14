#!/bin/bash

source postgres.env

docker exec -it $CONTAINER_NAME \
  psql --username $POSTGRES_USERNAME --dbname $DB_NAME
