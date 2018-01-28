FROM openjdk:8u131-jdk-alpine

WORKDIR /opt/cc
ADD build/libs/cc.jar /opt/cc/cc.jar

RUN apk add --no-cache coreutils

EXPOSE 8080

# Use exec form to run java as PID 1
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","cc.jar"]
