FROM openliberty/open-liberty:kernel-java${javaVersion}-openj9-ubi

COPY /src/main/liberty/config /config

COPY target/*.war /config/apps

RUN configure.sh