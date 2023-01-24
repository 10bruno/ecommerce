# Project "e-commerce"

Project created for study and aims to be a REST api of a CRUD:

---

### Objectives:

* Gradle project
* Creating and using two simultaneous database connections
* Connecting with PostgreSQL and MySql.
* Parameterization with application.yml
* Use of adapters
* Optional
* Throwing specific exceptions
* Interfaces
* OpenApiDoc
* CRUD
* Valid and Validated
* Flyway
* Handler exceptions
* Work with parameterizations
* Use of RedisDB

---

### Upgrade Plan
| Description                                     | Done?                 | Date       |
|-------------------------------------------------|-----------------------|------------|
| Upgrade Java version to 17                      | :heavy_check_mark:    | 27/09/2022 |
| Upgrade Gradle version to 7.5.1                 | :heavy_check_mark:    | 27/09/2022 |
| Upgrade Swagger 2.9 to OpenApiDoc 3.0           | :heavy_check_mark:    | 28/09/2022 |
| Include on Controller information of OpenApiDoc | :heavy_check_mark:    | 28/09/2022 |
| Translate project to English                    | :heavy_check_mark:    | 10/10/2022 |
| Change Controller Input Objects to DTO/Request  | :heavy_check_mark:    | 11/10/2022 |
| Create adapter layer                            | :heavy_check_mark:    | 11/10/2022 |
| Change project name to "ecommerce"              | :heavy_check_mark:    | 11/10/2022 |
| Create Handler exception controller             | :heavy_check_mark:    | 13/10/2022 |
| Include @Valid and @Validated to Controllers    | :heavy_check_mark:    | 09/01/2023 |
| Include flyway on project                       | :heavy_check_mark:    | 23/01/2023 |
| Upgrade Gradle version to 7.6                   | :heavy_check_mark:    | 23/01/2023 |
| Upgrade Java version to 19                      | :heavy_check_mark:    | 23/01/2023 |
| Upgrade Spring version to 3.0.2                 | :heavy_check_mark:    | 23/01/2023 |
| Create a local Docker for Mysql                 | :black_square_button: |            |
| Create a local Docker for Postgresql            | :black_square_button: |            |
| Finish unit tests                               | :black_square_button: |            |
| Create db scripts for flyway                    | :black_square_button: |            |
| Implement Spring Security                       | :black_square_button: |            |
| Create cache on RedisDB                         | :black_square_button: |            |
| Study implementation of the record type         | :black_square_button: |            |

---

### Pre-requisites

* Install Docker Engine and Docker Compose as standalone binaries
* Install Docker Desktop which includes both Docker Engine and Docker Compose

### Local execution

* Url Swagger: http://localhost:8080/ecommerce/swagger-ui/index.html#/
* Include to VM Options: -Dspring.profiles.active=local

### References

* https://springdoc.org/#swagger-ui-properties
* https://docs.spring.io/spring-security/reference/reactive/authorization/authorize-http-requests.html
