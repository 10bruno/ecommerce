# 🛒 E-commerce REST API

[![Java](https://img.shields.io/badge/Java-25%20LTS-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-7.4.1-59666C.svg)](https://hibernate.org/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.5-blue.svg)](https://gradle.org/)

> A comprehensive REST API e-commerce platform built for learning and demonstration purposes, featuring modern Spring Boot architecture with multiple database integrations and security. Also used as the pilot service for a Java 25 / Spring Boot 4 upgrade program.

## ✨ Features

- 🔒 **Spring Security 7** with HTTP Basic authentication
- 🗃️ **Multi-database support** (PostgreSQL + MySQL), each with its own `EntityManagerFactory`/`TransactionManager`
- 📚 **OpenAPI 3** documentation with Swagger UI (springdoc-openapi 3.0.3)
- 🔄 **Database migrations** with Flyway
- ✅ **Comprehensive validation** with Bean Validation
- 🐳 **Docker support** for local development
- 🧪 **Golden-file regression tests**: JSON serialization contracts + a SQL baseline captured via Testcontainers (MySQL + PostgreSQL running simultaneously)
- 📈 **Exception handling** with global error responses

## 🚀 Quick Start

### Prerequisites

- ☕ Java 25 (the Gradle toolchain auto-provisions the JDK — a local install isn't strictly required)
- 🐳 Docker or Docker-compatible runtime (Podman works too, via `DOCKER_HOST`)
- 🔧 IDE with Gradle support

### 🏃‍♂️ Running Locally

1. **Start the databases**
   ```bash
   docker compose -f src/main/resources/docker-compose.yml up
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the API**
   - 📖 **Swagger UI**: http://localhost:8080/ecommerce/swagger-ui/index.html
   - 🔐 **Authentication**: `usuario:senha`

### 🔐 Authentication Example

```powershell
# PowerShell
$headers = @{Authorization = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("usuario:senha"))}
Invoke-RestMethod -Uri "http://localhost:8080/ecommerce/customer" -Method Get -Headers $headers
```

```bash
# cURL
curl -X GET "http://localhost:8080/ecommerce/customer" \
  -H "Authorization: Basic dXN1YXJpbzpzZW5oYQ=="
```

## 🏗️ Architecture

### 📂 Project Structure
```
src/
├── main/java/br/com/ecommerce/
│   ├── controller/          # REST Controllers
│   ├── domain/              # Domain Services, Entities & Repositories
│   ├── infra/                # Infrastructure (exception handling, etc.)
│   └── config/               # Configuration Classes (datasources, security, flyway)
└── test/
    ├── java/br/com/ecommerce/golden/   # JSON + SQL golden-file regression tests
    └── java/br/com/ecommerce/...       # Unit & slice tests
```

### 🗄️ Database Schema
- **PostgreSQL**: Customer, Inventory, Product management
- **MySQL**: Payment historic and transactions

## 🛠️ Technology Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 25 LTS |
| **Framework** | Spring Boot 4.1.0 (Spring Framework 7.0.8) |
| **Persistence** | Hibernate ORM 7.4.1, Spring Data JPA |
| **Serialization** | Jackson 3.1.4 (native defaults — no Jackson 2 compatibility bridge) |
| **Security** | Spring Security 7.1.0 |
| **Databases** | PostgreSQL, MySQL |
| **Migration** | Flyway (via `spring-boot-starter-flyway`) |
| **Documentation** | OpenAPI 3 via springdoc-openapi 3.0.3 |
| **Build Tool** | Gradle 8.14.5 |
| **Testing** | JUnit 5, Testcontainers 2.x (MySQL + PostgreSQL), AssertJ, Mockito |
| **Containerization** | Docker / Docker Compose |

## 📋 Development Roadmap

### ✅ Completed — Java 25 / Spring Boot 4 upgrade program
- [x] Gradle wrapper 7.6 → 8.14.5
- [x] Java 19 → 21 → 25 LTS
- [x] JSON golden-file tests (Customer, Product, Inventory, PaymentHistoric) — request & response contracts
- [x] Dual Testcontainers (MySQL + PostgreSQL simultaneously) with a captured SQL baseline
- [x] Spring Boot 3.5.16 → 4.1.0 (Spring Framework 7, Jakarta EE 11, Spring Security 7, Hibernate 7 all came bundled)
- [x] Jackson 2 → 3 migration, including turning off the `spring.jackson.use-jackson2-defaults` compatibility bridge
- [x] Multi-database configuration (PostgreSQL + MySQL)
- [x] Docker containerization
- [x] Spring Security implementation

### 🚧 Partially done
- [ ] Java Records — started (`ErrorResponse`), not yet adopted across the other DTOs

### 🔮 Future Enhancements
- [ ] Redis caching layer
- [ ] Apache Kafka integration
- [ ] MongoDB integration
- [ ] Scheduled processing
- [ ] Modular monolith refactoring

## 🧪 Testing

163 tests across unit, slice, and golden-file layers.

```bash
# Run all tests
./gradlew clean test

# Run with coverage
./gradlew clean test jacocoTestReport

# Run specific test class
./gradlew test --tests CustomerControllerTest

# Run only the golden-file regression suite (needs a Docker-compatible runtime for the SQL baseline test)
./gradlew test --tests "br.com.ecommerce.golden.*"
```

> The `DualDatabasePersistenceGoldenTest` spins up real MySQL and PostgreSQL containers via Testcontainers. It works with Docker or Podman (set `DOCKER_HOST` accordingly if using Podman).

## 📊 API Endpoints

| Resource | Method | Path | Auth Required |
|----------|--------|------|----------------|
| Customer | GET | `/customer` | ✅ |
| Customer | GET | `/customer/{cpf}` | ✅ |
| Customer | POST | `/customer` | ✅ |
| Customer | PUT | `/customer` | ✅ |
| Customer | DELETE | `/customer/{cpf}` | ✅ |
| Product | GET | `/product` | ✅ |
| Product | GET | `/product/{id}` | ✅ |
| Product | POST | `/product` | ✅ |
| Product | PUT | `/product` | ✅ |
| Product | DELETE | `/product/{id}` | ✅ |
| Inventory | GET | `/inventory` | ✅ |
| Inventory | GET | `/inventory/{id}` | ✅ |
| Inventory | POST | `/inventory` | ✅ |
| Inventory | PUT | `/inventory` | ✅ |
| Inventory | DELETE | `/inventory/{id}` | ✅ |
| Payment Historic | GET | `/payment-historic` | ✅ |
| Payment Historic | GET | `/payment-historic/{id}` | ✅ |
| Payment Historic | POST | `/payment-historic` | ✅ |
| Payment Historic | PUT | `/payment-historic` | ✅ |
| Payment Historic | DELETE | `/payment-historic/{id}` | ✅ |
| Parameter | GET | `/parameter/list` | ❌ |
| Parameter | GET | `/parameter/sequence` | ❌ |
| Docs | GET | `/swagger-ui/**` | ❌ |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📚 References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [OpenAPI 3.0 Specification](https://springdoc.org/v2/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [Testcontainers Documentation](https://testcontainers.com/)
