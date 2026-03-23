#!/bin/bash
mvn clean package -Dmaven.test.skip=true;
docker compose -f docker-compose.yml up -d --build