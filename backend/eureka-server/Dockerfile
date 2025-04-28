FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/eureka-server-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sv-eureka.jar 

ENTRYPOINT ["java", "-jar", "sv-eureka.jar"]