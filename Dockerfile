# Stage 1: Build with maven to produce the .war application
FROM icr.io/appcafe/open-liberty-devfile-stack:21.0.0.12 as build

USER root
COPY src /src
COPY pom.xml /
RUN mvn -B package

USER 1001

# Stage 2: Place application on a liberty server
# TODO: Invest in using a kernel build and use the feature manager to install 
# needed Liberty features
FROM icr.io/appcafe/open-liberty:25.0.0.8-kernel-slim-java8-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

COPY --chown=1001:0 server.xml /config/

# This script will add the requested XML snippets to enable Liberty features and grow image to be fit-for-purpose 
# using featureUtility. 
# Only available in 'kernel-slim'. The 'full' tag already includes all features for convenience.
RUN features.sh

COPY --from=build --chown=1001:0 /target/openliberty-starter-1.0-SNAPSHOT.war /config/apps/

# This script will add the requested server configurations, apply any interim fixes and populate caches to
# optimize runtime
RUN configure.sh
