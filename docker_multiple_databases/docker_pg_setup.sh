#!/usr/bin/env bash

set -e
set -u
set -x

echo "Start database restoration"

pg_restore -p 5432 -U "$POSTGRES_USER" -v -d keycloak_db -Fc /docker-entrypoint-initdb.d/backup
pg_restore -p 5432 -U "$POSTGRES_USER" -v -d backend_db -Fc /docker-entrypoint-initdb.d/backup-backend

set +x
