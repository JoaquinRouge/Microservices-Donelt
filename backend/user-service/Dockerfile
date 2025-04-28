FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/user-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sv-user.jar

ENTRYPOINT ["java", "-jar", "sv-user.jar"]