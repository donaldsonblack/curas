![My Skills](https://skillicons.dev/icons?i=java,spring,postgres,docker,aws)

# Curas Server

Curas Server is the backend service for the **Curas** hospital management system. It provides REST APIs for users, departments, and other hospital resources. The service is built with modern Spring Boot features, uses PostgreSQL as the database, and integrates with AWS Cognito for authentication.

---

## Technology Stack

- **Java 21 / Spring Boot 3**
- **Spring Security** with JWT / AWS Cognito integration
- **Spring Data JPA** (Hibernate) for ORM
- **PostgreSQL** as the primary database
- **Gradle Kotlin DSL** for builds
- **Docker** for containerized deployment
- **Swagger/OpenAPI** for API documentation (`/docs` available in dev environment)

---

## Getting Started

### Requirements

- Java 21
- Gradle 8+
- Docker (optional, for containerized builds)
- PostgreSQL (or access to a running database)
- Git

---

## Build and Run

### Using Gradle

To build the JAR:

```bash
./gradlew clean bootJar
```

Run the application locally:

```bash
./gradlew bootRun
```

The server will start on **http://localhost:8080**.  
API documentation is available at **http://localhost:8080/docs** (dev environment only).

---

### Using Docker

Build the Docker image:

```bash
docker build -t curas-server .
```

Run the container:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<db_host>:5432/<db_name> \
  -e SPRING_DATASOURCE_USERNAME=<db_user> \
  -e SPRING_DATASOURCE_PASSWORD=<db_password> \
  curas-server
```

- Replace `<db_host>`, `<db_name>`, `<db_user>`, and `<db_password>` with your PostgreSQL credentials.  
- API will be exposed on **http://localhost:8080**.

---

## Environment Configuration

Spring profiles are used to differentiate environments:

- `dev` – Development environment (Swagger UI available)
- `prod` – Production environment

Configure properties in `src/main/resources/application-<profile>.properties` or via environment variables.

---

## Development Notes

- **Versioning**: The project version is controlled in `gradle.properties`.
- **Logging**: Configured with SLF4J / Logback.
- **Authorization**: Pre-authorization checks using `@PreAuthorize` and JWT claims.

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for branch naming conventions, commit messages, and code style guidelines.

---

