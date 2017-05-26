#!/bin/bash

URI=${1:-mongodb://experiment:experiment@benchmarking-shard-00-00-annli.mongodb.net:27017,benchmarking-shard-00-01-annli.mongodb.net:27017,benchmarking-shard-00-02-annli.mongodb.net:27017/bare-metal?ssl=true&replicaSet=benchmarking-shard-0&authSource=admin&journal=true&w=1&readConcernLevel=local&readPreference=secondaryPreferred&appName=bare-metal-producer}
MESSAGE_COUNT=${2:-1024}
PAYLOAD_SIZE=${3:-1024}

CMD="docker run \
            --env spring_data_mongodb_uri=${URI} \
            --interactive  \
            --name mongodb-producer \
            --network host \
            --rm \
            kurron/mongodb-bare-metal-producer:latest \
            --number-of-messages=${MESSAGE_COUNT} \
            --payload-size=${PAYLOAD_SIZE}"

echo ${CMD}
${CMD}
