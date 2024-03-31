#!/bin/bash

# Stop Redis instances
docker-compose stop redis-master-1 redis-master-2 redis-master-3 redis-replica-1 redis-replica-2 redis-replica-3

# Remove Redis data
docker-compose rm -f redis-master-1 redis-master-2 redis-master-3 redis-replica-1 redis-replica-2 redis-replica-3

# Recreate Redis instances
docker-compose up -d redis-master-1 redis-master-2 redis-master-3 redis-replica-1 redis-replica-2 redis-replica-3

# Wait for Redis instances to be ready
sleep 10

# Create Redis cluster
docker-compose exec redis-master-1 redis-cli --cluster create \
127.0.0.1:7001 \
127.0.0.1:7002 \
127.0.0.1:7003 \
127.0.0.1:7004 \
127.0.0.1:7005 \
127.0.0.1:7006 \
--cluster-yes --cluster-replicas 1