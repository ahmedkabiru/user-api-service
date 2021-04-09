FROM openjdk:16-jdk-alpine

# Add Maintainer Info
LABEL maintainer="opeyemi.kabiru@yahoo.com"

ARG JAR_FILE=target/user-service.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]