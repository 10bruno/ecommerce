package br.com.ecommerce.controller;

import br.com.ecommerce.domain.service.PaymentHistoricService;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(PaymentHistoricController.class)
@AutoConfigureMockMvc
class PaymentHistoricControllerTest {

    @MockBean
    private PaymentHistoricService paymentHistoricService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve retornar 200 quando localizar um histórico de pagamento válido.")
    void cenario1() throws Exception {
        //given ou arrange
        var idPayment = 1;
        var paymentHistoricResponse = MockBuilders.getHistoricResponseFirst();

        //when ou act
        when(this.paymentHistoricService.retrieveHistoric(idPayment)).thenReturn(paymentHistoricResponse);
        var response = mvc
                .perform(get("/payment-historic/" + idPayment))
                .andReturn()
                .getResponse();

        //then ou assert
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @DisplayName("Deve retornar 404 quando receber um id de histórico e não encontrar.")
    void cenario2() throws Exception {
        //given ou arrange
        var idPayment = 1;

        //when ou act
        doThrow(PaymentHistoricNotFoundException.class)
                .when(this.paymentHistoricService).retrieveHistoric(idPayment);

        var response = mvc
                .perform(get("/payment-historic/" + idPayment))
                .andReturn()
                .getResponse();

        //then ou assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }
}
