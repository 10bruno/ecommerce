package br.com.ecommerce.golden;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductGoldenTest {

    @Autowired
    private JacksonTester<ProductRequest> requestJson;

    @Autowired
    private JacksonTester<ProductResponse> responseJson;

    private ProductRequest fixtureRequest() {
        return ProductRequest.builder()
                .code("AB1234")
                .category("Electronics")
                .title("Test Product")
                .description("Test Product Description")
                .weight(new BigDecimal("1.50"))
                .dateRegister(LocalDate.of(2026, 7, 19))
                .build();
    }

    private ProductResponse fixtureResponse() {
        return ProductResponse.builder()
                .code("AB1234")
                .category("Electronics")
                .title("Test Product")
                .description("Test Product Description")
                .weight(new BigDecimal("1.50"))
                .dateRegister(LocalDate.of(2026, 7, 19))
                .build();
    }

    @Test
    void serializacaoDeProductRequest_naoMudou() throws Exception {
        assertThat(requestJson.write(fixtureRequest()))
                .isEqualToJson(new ClassPathResource("golden/product-request.json"));
    }

    @Test
    void serializacaoDeProductResponse_naoMudou() throws Exception {
        assertThat(responseJson.write(fixtureResponse()))
                .isEqualToJson(new ClassPathResource("golden/product-response.json"));
    }

    @Test
    void roundTripDeProductRequest_preservaTiposEValores() throws Exception {
        String jsonContent = requestJson.write(fixtureRequest()).getJson();
        ProductRequest desserializado = requestJson.parseObject(jsonContent);

        assertThat(desserializado.getWeight()).isEqualByComparingTo(new BigDecimal("1.50"));
        assertThat(desserializado.getDateRegister()).isEqualTo(LocalDate.of(2026, 7, 19));
        assertThat(desserializado.getCode()).isEqualTo("AB1234");
    }
}