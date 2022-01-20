#!/bin/bash
VERSION=0.0.3
IMAGE=us.icr.io/openlibertyio/start.openliberty.io

echo "Building ${IMAGE} ${VERSION}"
docker build --pull --build-arg VERSION=$VERSION --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') -t ${IMAGE}:${VERSION} .
