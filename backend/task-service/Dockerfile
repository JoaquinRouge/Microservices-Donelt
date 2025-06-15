FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/Task-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} sv-task.jar
ENTRYPOINT ["java", "-jar", "sv-task.jar"]
