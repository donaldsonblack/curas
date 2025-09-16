FROM eclipse-temurin:21-jdk AS build
WORKDIR /
COPY ../gradlew .
COPY ../gradle gradle
COPY ../build.gradle.kts settings.gradle.kts ./
COPY ../.git .git
COPY src src
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin
LABEL org.opencontainers.image.source="https://github.com/donaldsonblack/cura"
WORKDIR /curas
COPY --from=build /build/libs/*.jar cura.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cura.jar"]
