FROM openjdk:8-jdk-alpine

WORKDIR /app/

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} patchmaker-1.0.0.jar

EXPOSE 8080

RUN mkdir -p /doc/installscripts
 
ENTRYPOINT [ "java","-jar","patchmaker-1.0.0.jar" ]