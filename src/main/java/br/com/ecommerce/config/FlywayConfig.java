package br.com.ecommerce.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FlywayConfig {

    @Value("${postgres.datasource.jdbc-url}")
    String firstDatasourceUrl;
    @Value("${postgres.datasource.username}")
    String firstDatasourceUser;
    @Value("${postgres.datasource.password}")
    String firstDatasourcePassword;

    @PostConstruct
    public void migrateFlyway() {
        Flyway flywayIntegration = Flyway.configure()
                .dataSource(firstDatasourceUrl, firstDatasourceUser, firstDatasourcePassword)
                .locations("filesystem:./src/main/resources/db/postgresql/")
                .baselineOnMigrate(true)
                .load();

        flywayIntegration.migrate();
    }

}