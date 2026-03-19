#!/bin/bash
mvn clean package -Dmaven.test.skip=true;
docker compose ../docker-compose.yml up --build