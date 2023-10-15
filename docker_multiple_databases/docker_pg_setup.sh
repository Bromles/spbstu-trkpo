#!/usr/bin/env bash

set -e
set -u
set -x

pg_restore -p 5432 -U "$POSTGRES_USER" -v -d keycloak_db -Fc /docker-entrypoint-initdb.d/dump-keycloak_db-202310160210.sql

set +x
