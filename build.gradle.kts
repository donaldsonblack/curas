plugins {
    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
    id("application")
}

group = "dev.donaldsonblack"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.postgresql:postgresql:42.7.3") 
    implementation(platform("software.amazon.awssdk:bom:2.27.21"))

    // AWS SDK for Java v2
    implementation(platform("software.amazon.awssdk:bom:2.25.8"))

    // AWS SDK for Secrets Manager
    implementation("software.amazon.awssdk:secretsmanager")
    implementation("software.amazon.awssdk:auth")
    implementation("software.amazon.awssdk:regions")
    implementation("software.amazon.awssdk:aws-core")

    implementation("org.json:json:20090211")

    // HikariCP datasource
    implementation("com.zaxxer:HikariCP")
    
    // Use for JWT and cognito authentication lateer
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "dev.donaldsonblack.cura.Cura"
    }
}

application {
    mainClass.set("dev.donaldsonblack.cura.Cura")
}
