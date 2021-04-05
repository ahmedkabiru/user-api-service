FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="opeyemi.kabiru@yahoo.com"

ARG JAR_FILE=target/onewa-user-service.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]