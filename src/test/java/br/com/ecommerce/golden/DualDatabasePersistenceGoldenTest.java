package br.com.ecommerce.golden;

import br.com.ecommerce.config.MysqlConfig;
import br.com.ecommerce.config.PostgresConfig;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.domain.repository.mysql.PaymentHistoricRepository;
import br.com.ecommerce.domain.repository.postgres.CustomerRepository;
import br.com.ecommerce.domain.repository.postgres.InventoryRepository;
import br.com.ecommerce.domain.repository.postgres.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testcontainers duplo (MySQL + Postgres simultaneos), espelhando o
 * desenho real de producao: PostgresConfig/MysqlConfig sao as mesmas
 * classes de configuracao usadas pela aplicacao, cada uma com seu
 * EntityManagerFactory/TransactionManager independente.
 *
 * ddl-auto=create (nao create-drop) e usado apenas neste contexto de
 * teste (via @DynamicPropertySource) porque nao ha nenhum script CREATE
 * TABLE no repositorio -- os arquivos Flyway existentes contem so
 * INSERT e pressupoem schema pre-existente. As tabelas aqui sao geradas
 * pelo proprio Hibernate a partir das @Entity reais, nao inventadas.
 * "create" (sem drop) evita que o Hibernate tente DROP TABLE no
 * shutdown do contexto Spring, momento em que os containers
 * Testcontainers ja foram derrubados -- com create-drop isso gerava
 * dois timeouts de 30s (HikariPool "Connection is not available") no
 * final da execucao, mesmo com o teste passando.
 *
 * FlywayAutoConfiguration e excluido de proposito: o Flyway real da
 * aplicacao roda via FlywayConfig (@PostConstruct manual, fora do
 * mecanismo automatico do Boot), que nao esta no escopo deste teste;
 * sem a exclusao, o auto-config do Boot tentaria migrar a partir do
 * local padrao classpath:db/migration, que nao existe neste projeto.
 *
 * SqlCapturingStatementInspector captura, via
 * hibernate.session_factory.statement_inspector, cada SQL que o
 * Hibernate executa. Cada teste compara o SQL capturado contra um
 * arquivo de referencia versionado em src/test/resources/sql-baseline/
 * (mesmo principio dos golden files de JSON, mas para SQL -- serve de
 * baseline pre-Hibernate-7) e verifica a contagem de SELECT/INSERT por
 * operacao como guarda contra N+1 futuro.
 */
@Testcontainers
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {PostgresConfig.class, MysqlConfig.class})
class DualDatabasePersistenceGoldenTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("pagamentos")
            .withUsername("pagamentos")
            .withPassword("pagamentos");

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("pagamentos")
            .withUsername("pagamentos")
            .withPassword("pagamentos");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("postgres.datasource.jdbc-url", postgres::getJdbcUrl);
        registry.add("postgres.datasource.username", postgres::getUsername);
        registry.add("postgres.datasource.password", postgres::getPassword);
        registry.add("postgres.datasource.driverClassName", postgres::getDriverClassName);

        registry.add("mysql.datasource.jdbc-url", mysql::getJdbcUrl);
        registry.add("mysql.datasource.username", mysql::getUsername);
        registry.add("mysql.datasource.password", mysql::getPassword);
        registry.add("mysql.datasource.driverClassName", mysql::getDriverClassName);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.jpa.properties.hibernate.session_factory.statement_inspector",
                SqlCapturingStatementInspector.class::getName);
    }

    @BeforeEach
    void limpaSqlCapturado() {
        SqlCapturingStatementInspector.clear();
    }

    private void assertSqlBaseline(String resourceName) throws IOException {
        String sqlCapturado = String.join("\n", SqlCapturingStatementInspector.captured());
        String sqlEsperado = new ClassPathResource("sql-baseline/" + resourceName)
                .getContentAsString(StandardCharsets.UTF_8)
                .replace("\r\n", "\n");

        assertThat(sqlCapturado.strip()).isEqualTo(sqlEsperado.strip());
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentHistoricRepository paymentHistoricRepository;

    @Test
    void gravaELeCustomer_noPostgres() throws IOException {
        CustomerEntity fixture = new CustomerEntity();
        fixture.setCpf("12345678910");
        fixture.setName("Bruno");
        fixture.setBirthDate("15111989");
        fixture.setGender("M");

        customerRepository.saveAndFlush(fixture);

        Optional<CustomerEntity> encontrado = customerRepository.findById("12345678910");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getName()).isEqualTo("Bruno");
        assertThat(encontrado.get().getBirthDate()).isEqualTo("15111989");
        assertThat(encontrado.get().getGender()).isEqualTo("M");

        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("select")).isEqualTo(2);
        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("insert")).isEqualTo(1);
        assertSqlBaseline("customer-insert.sql");
    }

    @Test
    void gravaELeInventory_noPostgres() throws IOException {
        InventoryEntity fixture = new InventoryEntity();
        fixture.setId(1);
        fixture.setAvailableQuantity(new BigDecimal("100"));
        fixture.setReservedQuantity(new BigDecimal("10"));
        fixture.setProductCode("AB1234");

        inventoryRepository.saveAndFlush(fixture);

        Optional<InventoryEntity> encontrado = inventoryRepository.findById(1);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getAvailableQuantity()).isEqualByComparingTo(new BigDecimal("100"));
        assertThat(encontrado.get().getReservedQuantity()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(encontrado.get().getProductCode()).isEqualTo("AB1234");

        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("select")).isEqualTo(2);
        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("insert")).isEqualTo(1);
        assertSqlBaseline("inventory-insert.sql");
    }

    @Test
    void gravaELeProduct_noPostgres() throws IOException {
        ProductEntity fixture = new ProductEntity();
        fixture.setCode("AB1234");
        fixture.setCategory("Electronics");
        fixture.setTitle("Test Product");
        fixture.setDescription("Test Product Description");
        fixture.setWeight(new BigDecimal("1.50"));
        fixture.setDateRegister(LocalDate.of(2026, 7, 19));

        productRepository.saveAndFlush(fixture);

        Optional<ProductEntity> encontrado = productRepository.findById("AB1234");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getWeight()).isEqualByComparingTo(new BigDecimal("1.50"));
        assertThat(encontrado.get().getDateRegister()).isEqualTo(LocalDate.of(2026, 7, 19));

        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("select")).isEqualTo(2);
        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("insert")).isEqualTo(1);
        assertSqlBaseline("product-insert.sql");
    }

    @Test
    void gravaELePaymentHistoric_noMysql() throws IOException {
        PaymentHistoricEntity fixture = new PaymentHistoricEntity();
        fixture.setId(1);
        fixture.setDescription("Test Payment");
        fixture.setType("CREDIT_CARD");
        fixture.setDate(LocalDate.of(2026, 7, 19));

        paymentHistoricRepository.saveAndFlush(fixture);

        Optional<PaymentHistoricEntity> encontrado = paymentHistoricRepository.findById(1);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getDescription()).isEqualTo("Test Payment");
        assertThat(encontrado.get().getType()).isEqualTo("CREDIT_CARD");
        assertThat(encontrado.get().getDate()).isEqualTo(LocalDate.of(2026, 7, 19));

        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("select")).isEqualTo(2);
        assertThat(SqlCapturingStatementInspector.countStatementsStartingWith("insert")).isEqualTo(1);
        assertSqlBaseline("payment-historic-insert.sql");
    }
}
