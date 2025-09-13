package br.com.ecommerce.config;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostgresConfigTest {

    @InjectMocks
    private PostgresConfig postgresConfig;

    @Mock
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Mock
    private EntityManagerFactoryBuilder.Builder builder;

    @Mock
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldCreatePostgresDataSource_whenConfigurationIsValid() {
        DataSource result = postgresConfig.postgresDataSource();

        assertNotNull(result);
    }

    @Test
    void shouldCreatePostgresEntityManagerFactory_whenValidBuilderAndDataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/test")
                .username("test")
                .password("test")
                .build();

        when(entityManagerFactoryBuilder.dataSource(any(DataSource.class))).thenReturn(builder);
        when(builder.packages(anyString())).thenReturn(builder);
        when(builder.persistenceUnit(anyString())).thenReturn(builder);
        when(builder.build()).thenReturn(entityManagerFactoryBean);

        LocalContainerEntityManagerFactoryBean result = postgresConfig.postgresEntityManagerFactory(entityManagerFactoryBuilder, dataSource);

        assertNotNull(result);
        assertEquals(entityManagerFactoryBean, result);
        verify(entityManagerFactoryBuilder, times(1)).dataSource(dataSource);
        verify(builder, times(1)).packages("br.com.ecommerce.domain.entity.postgres");
        verify(builder, times(3)).persistenceUnit(anyString());
        verify(builder, times(1)).build();
    }

    @Test
    void shouldCreatePostgresTransactionManager_whenValidEntityManagerFactory() {
        PlatformTransactionManager result = postgresConfig.postgresTransactionManager(entityManagerFactory);

        assertNotNull(result);
    }
}