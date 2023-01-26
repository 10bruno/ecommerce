package br.com.ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${postgres.datasource.jdbc-url}")
    String firstDatasourceUrl;
    @Value("${postgres.datasource.username}")
    String firstDatasourceUser;
    @Value("${postgres.datasource.password}")
    String firstDatasourcePassword;

    @PostConstruct
    public void migrateFirstFlyway() {
        Flyway flywayIntegration = Flyway.configure()
                .dataSource(firstDatasourceUrl, firstDatasourceUser, firstDatasourcePassword)
                .locations("filesystem:./src/main/resources/db/postgresql/flyway/")
                .baselineOnMigrate(true)
                .load();

        flywayIntegration.migrate();
    }

    @Value("${mysql.datasource.jdbc-url}")
    String secondDatasourceUrl;
    @Value("${mysql.datasource.username}")
    String secondDatasourceUser;
    @Value("${mysql.datasource.password}")
    String secondDatasourcePassword;

    @PostConstruct
    public void migrateSecondtFlyway() {
        Flyway flywayIntegration = Flyway.configure()
                .dataSource(secondDatasourceUrl, secondDatasourceUser, secondDatasourcePassword)
                .locations("filesystem:./src/main/resources/db/mysql/flyway/")
                .baselineOnMigrate(true)
                .load();

        flywayIntegration.migrate();
    }

}