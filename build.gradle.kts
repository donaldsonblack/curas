plugins {
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("java")
    id("application")
    id("com.gorylenko.gradle-git-properties") version "2.5.2"
}

group = "dev.donaldsonblack"
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories { mavenCentral() }

dependencies {
    // --- Spring Boot ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Data layers (both enabled while you migrate)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")    // JPA/Hibernate

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.nimbusds:nimbus-jose-jwt:10.4.2") // optional low-level JWT utils

    // Database driver (implementation so PGobject is available at compile time)
    implementation("org.postgresql:postgresql")

    // JSON / JSONB
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("org.json:json:20240303") // keep org.json (legacy usages)

    // AWS SDK v2 (single BOM)
    implementation(platform("software.amazon.awssdk:bom:2.33.0"))
    implementation("software.amazon.awssdk:secretsmanager")
    implementation("software.amazon.awssdk:auth")
    implementation("software.amazon.awssdk:regions")
    implementation("software.amazon.awssdk:aws-core")

    // OpenAPI UI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.11")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.jar {
    manifest { attributes["Main-Class"] = "dev.donaldsonblack.cura.Cura" }
}

application {
    mainClass.set("dev.donaldsonblack.cura.Cura")
}
