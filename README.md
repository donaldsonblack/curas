![My Skills](https://skillicons.dev/icons?i=java,spring,postgres,docker,aws)

# Curas

This repo hosts the backend server for Curas, the equipment management system. It provides REST APIs Equipment, Departments, Users etc. It is paired with the Curas-web frontend.

> [!IMPORTANT]
> The Curas server uses Java 25 LTS and as such must be installed to build and run the applicaion. The docker image uses the Eclipse Temurin JDK / JRE.

---

## Stack

- **Java 25 / Spring Boot 3**
- **Spring Security** with JWT / AWS Cognito integration
- **Spring Data JPA** (Hibernate) for ORM
- **PostgreSQL** as the primary database
- **Gradle Kotlin DSL** for builds
- **Docker** for containerized deployment
- **Swagger/OpenAPI** for API documentation (`/docs` available in dev environment)

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for branch naming conventions, commit messages, and code style guidelines.

---

