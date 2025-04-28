FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/api-gateway-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sv-api-gateway.jar

ENTRYPOINT ["java", "-jar", "sv-api-gateway.jar"]