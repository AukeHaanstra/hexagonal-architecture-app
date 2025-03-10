#!/bin/bash

source ../../main/env/.env

docker run --rm --network="host"\
  -e POSTGRES_URL=$POSTGRES_URL \
  -e POSTGRES_USERNAME=$POSTGRES_USERNAME \
  -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
  -e DEFAULT_LOGGING_LEVEL=$DEFAULT_LOGGING_LEVEL \
  hexagonal-architecture-app:latest
