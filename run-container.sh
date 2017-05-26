#!/bin/bash

URI=${1:-mongodb://localhost:27017/bare-metal?safe=true&w=majority&readPreference=nearest&appName=bare-metal-producer}
MESSAGE_COUNT=${2:-2500}
PAYLOAD_SIZE=${3:-1024}

CMD="docker run \
            --cpus 1 \
            --env spring_data_mongodb_uri=${URI} \
            --interactive  \
            --name mongodb-producer \
            --network host \
            --rm \
            --tty \
            kurron/mongodb-bare-metal-producer:latest \
            --number-of-messages=${MESSAGE_COUNT} \
            --payload-size=${PAYLOAD_SIZE}"

echo ${CMD}
${CMD}