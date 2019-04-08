#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src/ /build/src/
RUN mvn package

#
# Package stage
#
FROM nginx:alpine-perl
RUN apk add --no-cache graphviz

#JRE
RUN apk add --no-cache openjdk8-jre fontconfig ttf-dejavu font-bitstream-type1

EXPOSE 80
EXPOSE 9091
COPY --from=build /build/target/plantuml-service-1.0-SNAPSHOT.jar /usr/local/lib/plantuml-service.jar
COPY assembly/opt/ /opt
COPY assembly/etc/nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf
COPY assembly/html/ /usr/share/nginx/html
RUN ["chmod", "+x", "/opt/plantuml-service/plantuml-service-start.sh"]
ENTRYPOINT ["sh", "/opt/plantuml-service/plantuml-service-start.sh"]