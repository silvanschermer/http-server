#!/usr/bin/env bash

./gradlew build && java -jar build/libs/http-server-0.1.jar --port=80