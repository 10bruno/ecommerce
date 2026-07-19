package br.com.ecommerce.golden;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class InventoryGoldenTest {

    @Autowired
    private JacksonTester<InventoryRequest> requestJson;

    @Autowired
    private JacksonTester<InventoryResponse> responseJson;

    private InventoryRequest fixtureRequest() {
        return InventoryRequest.builder()
                .id(1)
                .availableQuantity(new BigDecimal("100"))
                .reservedQuantity(new BigDecimal("10"))
                .productCode("AB1234")
                .build();
    }

    private InventoryResponse fixtureResponse() {
        return InventoryResponse.builder()
                .id(1)
                .availableQuantity(new BigDecimal("100"))
                .reservedQuantity(new BigDecimal("10"))
                .productCode("AB1234")
                .build();
    }

    @Test
    void serializacaoDeInventoryRequest_naoMudou() throws Exception {
        assertThat(requestJson.write(fixtureRequest()))
                .isEqualToJson(new ClassPathResource("golden/inventory-request.json"));
    }

    @Test
    void serializacaoDeInventoryResponse_naoMudou() throws Exception {
        assertThat(responseJson.write(fixtureResponse()))
                .isEqualToJson(new ClassPathResource("golden/inventory-response.json"));
    }

    @Test
    void roundTripDeInventoryRequest_preservaTiposEValores() throws Exception {
        String jsonContent = requestJson.write(fixtureRequest()).getJson();
        InventoryRequest desserializado = requestJson.parseObject(jsonContent);

        assertThat(desserializado.getAvailableQuantity()).isEqualByComparingTo(new BigDecimal("100"));
        assertThat(desserializado.getReservedQuantity()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(desserializado.getProductCode()).isEqualTo("AB1234");
    }
}
