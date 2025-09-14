# 🛒 E-commerce REST API

[![Java](https://img.shields.io/badge/Java-19-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-7.6-blue.svg)](https://gradle.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A comprehensive REST API e-commerce platform built for learning and demonstration purposes, featuring modern Spring Boot architecture with multiple database integrations and security.

## ✨ Features

- 🔒 **Spring Security** with HTTP Basic authentication
- 🗃️ **Multi-database support** (PostgreSQL + MySQL)
- 📚 **OpenAPI 3.0** documentation with Swagger UI
- 🔄 **Database migrations** with Flyway
- ✅ **Comprehensive validation** with Bean Validation
- 🐳 **Docker support** for local development
- 🧪 **Unit & Integration tests** with high coverage
- 📈 **Exception handling** with global error responses

## 🚀 Quick Start

### Prerequisites

- ☕ Java 19+
- 🐳 Docker & Docker Compose
- 🔧 IDE with Gradle support

### 🏃‍♂️ Running Locally

1. **Start the databases**
   ```bash
   docker compose up
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun -Dspring.profiles.active=local
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
│   ├── domain/             # Domain Services & Models
│   ├── infra/              # Infrastructure (DB, Config)
│   └── config/             # Configuration Classes
└── test/                   # Unit & Integration Tests
```

### 🗄️ Database Schema
- **PostgreSQL**: Customer, Inventory, Product management
- **MySQL**: Payment historic and transactions

## 🛠️ Technology Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 19 |
| **Framework** | Spring Boot 3.0.2 |
| **Security** | Spring Security |
| **Databases** | PostgreSQL, MySQL |
| **Migration** | Flyway |
| **Documentation** | OpenAPI 3.0 |
| **Build Tool** | Gradle 7.6 |
| **Containerization** | Docker |

## 📋 Development Roadmap

### ✅ Completed Features
- [x] Java 19 & Spring Boot 3.0.2 upgrade
- [x] OpenAPI 3.0 documentation
- [x] Multi-database configuration
- [x] Docker containerization
- [x] Comprehensive unit tests
- [x] Spring Security implementation
- [x] Clean architecture refactoring

### 🚧 In Progress
- [ ] **Spring Security** *(Current Sprint)*
- [ ] Redis caching layer
- [ ] Java Records implementation

### 🔮 Future Enhancements
- [ ] Java 21 upgrade
- [ ] Apache Kafka integration
- [ ] MongoDB integration
- [ ] TestContainers implementation
- [ ] Scheduled processing
- [ ] Modular monolith refactoring

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport

# Run specific test class
./gradlew test --tests CustomerControllerTest
```

## 📊 API Endpoints

| Endpoint | Method | Description | Auth Required |
|----------|--------|-------------|---------------|
| `/customer` | GET | List all customers | ✅ |
| `/customer/{cpf}` | GET | Get customer by CPF | ✅ |
| `/customer` | POST | Create customer | ✅ |
| `/customer` | PUT | Update customer | ✅ |
| `/customer/{cpf}` | DELETE | Delete customer | ✅ |
| `/parameter/**` | ALL | Parameter management | ❌ |
| `/swagger-ui/**` | GET | API documentation | ❌ |

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
