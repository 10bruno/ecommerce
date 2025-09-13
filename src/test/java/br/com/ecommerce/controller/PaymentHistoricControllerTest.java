package br.com.ecommerce.controller;

import br.com.ecommerce.domain.service.PaymentHistoricService;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentHistoricController.class)
@AutoConfigureMockMvc
class PaymentHistoricControllerTest {

    @MockBean
    private PaymentHistoricService paymentHistoricService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 quando localizar um histórico de pagamento válido.")
    void cenario1() throws Exception {
        //given ou arrange
        var idPayment = 1;
        var paymentHistoricResponse = MockBuilders.getHistoricResponseFirst();

        //when ou act
        when(this.paymentHistoricService.retrieveHistoric(idPayment)).thenReturn(paymentHistoricResponse);

        //then ou assert
        mvc
                .perform(get("/payment-historic/" + idPayment))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 404 quando receber um id de histórico e não encontrar.")
    void cenario2() throws Exception {
        //given ou arrange
        var idPayment = 1;

        //when ou act
        doThrow(PaymentHistoricNotFoundException.class)
                .when(this.paymentHistoricService).retrieveHistoric(idPayment);

        //then ou assert
        mvc
                .perform(get("/payment-historic/" + idPayment))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 quando localizar uma lista de históricos de pagamentos válidos.")
    void cenario3() throws Exception {
        //given ou arrange
        var paymentHistoricResponseList = MockBuilders.buildListHistoricResponse();

        //when ou act
        when(this.paymentHistoricService.retrieveListHistorics()).thenReturn(paymentHistoricResponseList);

        //then ou assert
        mvc
                .perform(get("/payment-historic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(1)));
    }

    @Test
    @DisplayName("Deve retornar 200 quando atualizar um pagamento com sucesso")
    void cenario4() throws Exception {
        //given ou arrange
        var paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();
        var paymentHistoricCreated = MockBuilders.getHistoricResponseFirst();

        //when ou act
        when(this.paymentHistoricService.createHistoric(paymentHistoricRequest)).thenReturn(paymentHistoricCreated);

        //then ou assert
        mvc
                .perform(put("/payment-historic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentHistoricRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));

    }

    @Test
    @DisplayName("Deve retornar 201 quando criar um pagamento com sucesso")
    void cenario5() throws Exception {
        //given ou arrange
        var paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();
        var paymentHistoricCreated = MockBuilders.getHistoricResponseFirst();

        //when ou act
        when(this.paymentHistoricService.createHistoric(paymentHistoricRequest)).thenReturn(paymentHistoricCreated);

        //then ou assert
        mvc
                .perform(post("/payment-historic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentHistoricRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao criar um pagamento")
    void cenario6() throws Exception {
        //given ou arrange
        var paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();

        //when ou act
        doThrow(PaymentHistoricCreateException.class)
                .when(this.paymentHistoricService).createHistoric(paymentHistoricRequest);

        //then ou assert
        mvc
                .perform(post("/payment-historic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentHistoricRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 204 quando deletar um pagamento com sucesso")
    void cenario7() throws Exception {
        //given ou arrange
        var idPayment = 1;

        //when ou act
        doNothing().when(this.paymentHistoricService).deleteHistoric(idPayment);

        //then ou assert
        mvc
                .perform(delete("/payment-historic/" + idPayment))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao deletar um pagamento")
    void cenario8() throws Exception {
        //given ou arrange
        var idPayment = 1;

        //when ou act
        doThrow(PaymentHistoricDeleteException.class)
                .when(this.paymentHistoricService).deleteHistoric(idPayment);

        //then ou assert
        mvc
                .perform(delete("/payment-historic/" + idPayment))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao atualizar um pagamento")
    void cenario9() throws Exception {
        //given ou arrange
        var paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();

        //when ou act
        doThrow(PaymentHistoricCreateException.class)
                .when(this.paymentHistoricService).createHistoric(paymentHistoricRequest);

        //then ou assert
        mvc
                .perform(put("/payment-historic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentHistoricRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 404 quando não encontrar históricos de pagamentos")
    void cenario10() throws Exception {
        //given ou arrange

        //when ou act
        doThrow(PaymentHistoricNotFoundException.class)
                .when(this.paymentHistoricService).retrieveListHistorics();

        //then ou assert
        mvc
                .perform(get("/payment-historic"))
                .andExpect(status().isNotFound());
    }

}
