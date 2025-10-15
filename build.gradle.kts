import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "4.0.0-M3"
    id("io.spring.dependency-management") version "1.1.7"
    id("java")
    id("application")
    id("com.gorylenko.gradle-git-properties") version "2.5.2"
    id("com.diffplug.spotless") version "7.2.1"
}

group = "dev.donaldsonblack"
java { toolchain { languageVersion.set(JavaLanguageVersion.of(25)) } }

repositories { mavenCentral() }

dependencies {
    implementation("org.springframework:spring-core")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.boot:spring-boot-starter-cache")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.nimbusds:nimbus-jose-jwt:10.4.2")

    implementation("io.hypersistence:hypersistence-utils-hibernate-71:3.11.0")
    implementation("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.core:jackson-databind")
		implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("org.json:json:20240303")
    implementation(platform("software.amazon.awssdk:bom:2.33.0"))
    implementation("software.amazon.awssdk:secretsmanager")
    implementation("software.amazon.awssdk:auth")
    implementation("software.amazon.awssdk:regions")
    implementation("software.amazon.awssdk:aws-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.11")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.jar {
    manifest { attributes["Main-Class"] = "com.dblck.curas.Curas" }
}

application {
    mainClass.set("com.dblck.curas.Curas")
}

tasks.named<BootRun>("bootRun") {
    jvmArgs = listOf(
        "-XX:+UseCompactObjectHeaders",
    )
}

springBoot {
    buildInfo {
        properties {
            version = providers.gradleProperty("version")
        }
    }
}


spotless {
    java {
        googleJavaFormat("1.27.0").reflowLongStrings()
        target("**/*.java")
    }
    kotlin {
        ktfmt().googleStyle()
        target("**/*.kt")
    }
}
