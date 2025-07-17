# STAGE 1: Build mit Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# STAGE 2: Nur das fertige JAR in schlanken Java-Container
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
EXPOSE 5005
COPY --from=build /build/target/DlsaApi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]