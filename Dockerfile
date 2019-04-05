#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

#
# Package stage
#
FROM openjdk:8-jre-alpine
RUN apk add --no-cache graphviz
EXPOSE 80
COPY --from=build /build/target/plantuml-service-1.0-SNAPSHOT.jar /usr/local/lib/plantuml-service.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/plantuml-service.jar"]