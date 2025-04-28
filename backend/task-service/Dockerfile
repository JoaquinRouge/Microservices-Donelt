FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/Task-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sv-task.jar

ENTRYPOINT ["java", "-jar", "sv-task.jar"]