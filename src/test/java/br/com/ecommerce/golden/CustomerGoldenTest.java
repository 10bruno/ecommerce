package br.com.ecommerce.golden;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CustomerGoldenTest {

    @Autowired
    private JacksonTester<CustomerRequest> requestJson;

    @Autowired
    private JacksonTester<CustomerResponse> responseJson;

    @Test
    void serializacaoDeCustomerRequest_naoMudou() throws Exception {
        CustomerRequest fixture = CustomerRequest.builder()
                .cpf("12345678910")
                .name("Bruno")
                .birthDate("15111989")
                .gender("M")
                .build();

        assertThat(requestJson.write(fixture))
                .isEqualToJson(new ClassPathResource("golden/customer-request.json"));
    }

    @Test
    void serializacaoDeCustomerResponse_naoMudou() throws Exception {
        CustomerResponse fixture = CustomerResponse.builder()
                .cpf("12345678910")
                .name("Bruno")
                .birthDate("15111989")
                .gender("M")
                .build();

        assertThat(responseJson.write(fixture))
                .isEqualToJson(new ClassPathResource("golden/customer-response.json"));
    }

    @Test
    void roundTripDeCustomerRequest_preservaOsDados() throws Exception {
        String jsonContent = requestJson.write(CustomerRequest.builder()
                .cpf("12345678910")
                .name("Bruno")
                .birthDate("15111989")
                .gender("M")
                .build()).getJson();

        CustomerRequest desserializado = requestJson.parseObject(jsonContent);

        assertThat(desserializado.getCpf()).isEqualTo("12345678910");
        assertThat(desserializado.getName()).isEqualTo("Bruno");
        assertThat(desserializado.getBirthDate()).isEqualTo("15111989");
        assertThat(desserializado.getGender()).isEqualTo("M");
    }

    @Test
    void desserializacaoDeCustomerRequest_ignoraCampoJsonDesconhecido() throws Exception {
        String jsonComCampoExtra = "{\"cpf\":\"12345678910\",\"name\":\"Bruno\","
                + "\"birthDate\":\"15111989\",\"gender\":\"M\",\"campoInexistente\":\"x\"}";

        CustomerRequest desserializado = requestJson.parseObject(jsonComCampoExtra);

        assertThat(desserializado.getCpf()).isEqualTo("12345678910");
        assertThat(desserializado.getName()).isEqualTo("Bruno");
        assertThat(desserializado.getBirthDate()).isEqualTo("15111989");
        assertThat(desserializado.getGender()).isEqualTo("M");
    }
}