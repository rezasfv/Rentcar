#!/bin/bash
set -e

# Wait for PostgreSQL to be ready
until pg_isready -h "db" -p "5432" -U "postgres" ; do
  echo "Waiting for PostgreSQL to be ready..."
  sleep 2
done

# Create the database
psql -v ON_ERROR_STOP=1  -h "db" -p "5432"  --username "postgres" <<-EOSQL
  DROP DATABASE IF EXISTS rentcar;
  CREATE DATABASE rentcar ENCODING = 'UTF8';
  \i /docker-entrypoint-initdb.d/create-db.sql
EOSQL
