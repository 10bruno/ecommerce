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
class MysqlConfigTest {

    @InjectMocks
    private MysqlConfig mysqlConfig;

    @Mock
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Mock
    private EntityManagerFactoryBuilder.Builder builder;

    @Mock
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldCreateDataSource_whenConfigurationIsValid() {
        DataSource result = mysqlConfig.dataSource();

        assertNotNull(result);
    }

    @Test
    void shouldCreateEntityManagerFactory_whenValidBuilderAndDataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test")
                .username("test")
                .password("test")
                .build();

        when(entityManagerFactoryBuilder.dataSource(any(DataSource.class))).thenReturn(builder);
        when(builder.packages(anyString())).thenReturn(builder);
        when(builder.persistenceUnit(anyString())).thenReturn(builder);
        when(builder.build()).thenReturn(entityManagerFactoryBean);

        LocalContainerEntityManagerFactoryBean result = mysqlConfig.entityManagerFactory(entityManagerFactoryBuilder, dataSource);

        assertNotNull(result);
        assertEquals(entityManagerFactoryBean, result);
        verify(entityManagerFactoryBuilder, times(1)).dataSource(dataSource);
        verify(builder, times(1)).packages("br.com.ecommerce.domain.entity.mysql");
        verify(builder, times(1)).persistenceUnit("payment_historic");
        verify(builder, times(1)).build();
    }

    @Test
    void shouldCreateTransactionManager_whenValidEntityManagerFactory() {
        PlatformTransactionManager result = mysqlConfig.transactionManager(entityManagerFactory);

        assertNotNull(result);
    }
}