package br.com.ecommerce.controller;

import br.com.ecommerce.service.ParameterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParameterController.class)
@AutoConfigureMockMvc
class ParameterControllerTest {

    @MockBean
    private ParameterService parameterService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve retornar 200 quando buscar lista de parâmetros com sucesso.")
    void cenario1() throws Exception {
        //when ou act
        doNothing().when(this.parameterService).getListParameter();

        //then ou assert
        mvc
                .perform(get("/parameter/list"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 200 quando buscar sequência de parâmetros com sucesso.")
    void cenario2() throws Exception {
        //when ou act
        doNothing().when(this.parameterService).getSequenceParameter();

        //then ou assert
        mvc
                .perform(get("/parameter/sequence"))
                .andExpect(status().isOk());
    }
}