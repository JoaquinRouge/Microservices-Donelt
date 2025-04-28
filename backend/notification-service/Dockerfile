FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/notification-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sv-notification.jar

ENTRYPOINT ["java", "-jar", "sv-notification.jar"]