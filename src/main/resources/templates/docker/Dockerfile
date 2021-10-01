FROM openliberty/open-liberty:kernel-slim-java${javaVersion}-openj9-ubi

COPY --chown=1001:0 /src/main/liberty/config /config

COPY --chown=1001:0 ${buildPath}*.war /config/apps

RUN configure.sh
RUN features.sh