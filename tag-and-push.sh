#!/bin/bash

# use the time as a tag
UNIXTIME=$(date +%s)

# docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
docker tag mongodbbaremetalproducer_producer:latest kurron/mongodb-bare-metal-producer:latest
docker tag mongodbbaremetalproducer_producer:latest kurron/mongodb-bare-metal-producer:${UNIXTIME}
docker images

# Usage:  docker push [OPTIONS] NAME[:TAG]
docker push kurron/mongodb-bare-metal-producer:latest
docker push kurron/mongodb-bare-metal-producer:${UNIXTIME}
