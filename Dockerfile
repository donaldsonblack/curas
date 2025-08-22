# Multi-stage Dockerfile for Spring Boot using Eclipse Temurin

# 1. Build stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Gradle wrapper and build files
COPY ../gradlew .
COPY ../gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY src src

# Ensure the Gradle wrapper is executable, then build the jar
RUN chmod +x gradlew \
    && ./gradlew bootJar --no-daemon

# 2. Runtime stage
FROM eclipse-temurin:21-jre

# Link back to the GitHub source repository
LABEL org.opencontainers.image.source="https://github.com/donaldsonblack/cura-server"

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar cura.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "cura.jar"]