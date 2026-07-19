package br.com.ecommerce.golden;

import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PaymentHistoricGoldenTest {

    @Autowired
    private JacksonTester<PaymentHistoricRequest> requestJson;

    @Autowired
    private JacksonTester<PaymentHistoricResponse> responseJson;

    private PaymentHistoricRequest fixtureRequest() {
        return PaymentHistoricRequest.builder()
                .id(1)
                .description("Test Payment")
                .type("CREDIT_CARD")
                .date(LocalDate.of(2026, 7, 19))
                .build();
    }

    private PaymentHistoricResponse fixtureResponse() {
        return PaymentHistoricResponse.builder()
                .id(1)
                .description("Test Payment")
                .type("CREDIT_CARD")
                .date(LocalDate.of(2026, 7, 19))
                .build();
    }

    @Test
    void serializacaoDePaymentHistoricRequest_naoMudou() throws Exception {
        assertThat(requestJson.write(fixtureRequest()))
                .isEqualToJson(new ClassPathResource("golden/payment-historic-request.json"));
    }

    @Test
    void serializacaoDePaymentHistoricResponse_naoMudou() throws Exception {
        assertThat(responseJson.write(fixtureResponse()))
                .isEqualToJson(new ClassPathResource("golden/payment-historic-response.json"));
    }

    @Test
    void roundTripDePaymentHistoricRequest_preservaTiposEValores() throws Exception {
        String jsonContent = requestJson.write(fixtureRequest()).getJson();
        PaymentHistoricRequest desserializado = requestJson.parseObject(jsonContent);

        assertThat(desserializado.getDate()).isEqualTo(LocalDate.of(2026, 7, 19));
        assertThat(desserializado.getDescription()).isEqualTo("Test Payment");
        assertThat(desserializado.getType()).isEqualTo("CREDIT_CARD");
    }
}
