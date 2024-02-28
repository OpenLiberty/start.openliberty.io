FROM icr.io/appcafe/open-liberty:kernel-slim-java${javaVersion}-openj9-ubi${minimalImage}

COPY --chown=1001:0 /src/main/liberty/config /config

RUN features.sh

COPY --chown=1001:0 ${buildPath}*.war /config/apps

RUN configure.sh
