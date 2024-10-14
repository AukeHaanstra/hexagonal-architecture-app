#!/bin/bash

source postgres.env

docker run -d \
  --name $CONTAINER_NAME \
  --user $UUID \
  -e POSTGRES_PASSWORD=$PGPASSWORD \
  -e PGDATA=/var/lib/postgresql/data/pgdata \
  -e POSTGRES_USER=$POSTGRES_USERNAME \
  -p 5432:5432 \
  -v $PWD:/workdir \
  postgres
