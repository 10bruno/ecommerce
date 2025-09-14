package br.com.ecommerce.controller;

import br.com.ecommerce.config.WebSecurityConfig;
import br.com.ecommerce.domain.service.CustomerService;
import br.com.ecommerce.infra.exception.CustomerCreateException;
import br.com.ecommerce.infra.exception.CustomerDeleteException;
import br.com.ecommerce.infra.exception.CustomerNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "usuario", password = "senha", roles = "PAGAMENTOS")
@WebMvcTest(CustomerController.class)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 quando localizar um cliente por CPF válido.")
    void cenario1() throws Exception {
        //given ou arrange
        var cpf = "12345678910";
        var customerResponse = MockBuilders.getCustomerResponse();

        //when ou act
        when(this.customerService.retrieveCustomer(cpf)).thenReturn(customerResponse);

        //then ou assert
        mvc
                .perform(get("/customer/" + cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando receber um CPF e não encontrar o cliente.")
    void cenario2() throws Exception {
        //given ou arrange
        var cpf = "12345678910";

        //when ou act
        doThrow(CustomerNotFoundException.class)
                .when(this.customerService).retrieveCustomer(cpf);

        //then ou assert
        mvc
                .perform(get("/customer/" + cpf))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando receber um CPF com formato inválido.")
    void cenario3() throws Exception {
        //given ou arrange
        var cpfInvalido = "123";

        //then ou assert
        mvc
                .perform(get("/customer/" + cpfInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 quando localizar uma lista de clientes válidos.")
    void cenario4() throws Exception {
        //given ou arrange
        var customerResponseList = MockBuilders.buildListCustomerResponse();

        //when ou act
        when(this.customerService.retrieveListCustomers()).thenReturn(customerResponseList);

        //then ou assert
        mvc
                .perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    @DisplayName("Deve retornar 404 quando buscar lista de clientes e não encontrar nenhum.")
    void cenario5() throws Exception {
        //when ou act
        doThrow(CustomerNotFoundException.class)
                .when(this.customerService).retrieveListCustomers();

        //then ou assert
        mvc
                .perform(get("/customer"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 quando atualizar um cliente com sucesso")
    void cenario6() throws Exception {
        //given ou arrange
        var customerRequest = MockBuilders.buildCustomerRequest();
        var customerUpdated = MockBuilders.getCustomerResponse();

        //when ou act
        when(this.customerService.createCustomer(customerRequest)).thenReturn(customerUpdated);

        //then ou assert
        mvc
                .perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao atualizar um cliente")
    void cenario7() throws Exception {
        //given ou arrange
        var customerRequest = MockBuilders.buildCustomerRequest();

        //when ou act
        when(this.customerService.createCustomer(customerRequest))
                .thenThrow(new CustomerCreateException("Error creating customer", new RuntimeException()));

        //then ou assert
        mvc
                .perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar atualizar cliente com dados inválidos")
    void cenario8() throws Exception {
        //given ou arrange
        var customerRequestInvalido = MockBuilders.buildCustomerRequest();
        customerRequestInvalido.setCpf("123"); // CPF inválido

        //then ou assert
        mvc
                .perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 201 quando criar um cliente com sucesso")
    void cenario9() throws Exception {
        //given ou arrange
        var customerRequest = MockBuilders.buildCustomerRequest();
        var customerCreated = MockBuilders.getCustomerResponse();

        //when ou act
        when(this.customerService.createCustomer(customerRequest)).thenReturn(customerCreated);

        //then ou assert
        mvc
                .perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao criar um cliente")
    void cenario10() throws Exception {
        //given ou arrange
        var customerRequest = MockBuilders.buildCustomerRequest();

        //when ou act
        when(this.customerService.createCustomer(customerRequest))
                .thenThrow(new CustomerCreateException("Error creating customer", new RuntimeException()));

        //then ou assert
        mvc
                .perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar criar cliente com dados inválidos")
    void cenario11() throws Exception {
        //given ou arrange
        var customerRequestInvalido = MockBuilders.buildCustomerRequest();
        customerRequestInvalido.setName(""); // Nome obrigatório

        //then ou assert
        mvc
                .perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 204 quando deletar um cliente com sucesso")
    void cenario12() throws Exception {
        //given ou arrange
        var cpf = "12345678910";

        //when ou act
        doNothing().when(this.customerService).deleteCustomer(cpf);

        //then ou assert
        mvc
                .perform(delete("/customer/" + cpf))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao deletar um cliente")
    void cenario13() throws Exception {
        //given ou arrange
        var cpf = "12345678910";

        //when ou act
        doThrow(new CustomerDeleteException("Error deleting customer", new RuntimeException()))
                .when(this.customerService).deleteCustomer(cpf);

        //then ou assert
        mvc
                .perform(delete("/customer/" + cpf))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar deletar cliente com CPF inválido")
    void cenario14() throws Exception {
        //given ou arrange
        var cpfInvalido = "123";

        //then ou assert
        mvc
                .perform(delete("/customer/" + cpfInvalido))
                .andExpect(status().isBadRequest());
    }
}