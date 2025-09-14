package br.com.ecommerce.controller;

import br.com.ecommerce.config.WebSecurityConfig;
import br.com.ecommerce.domain.service.InventoryService;
import br.com.ecommerce.infra.exception.InventoryCreateException;
import br.com.ecommerce.infra.exception.InventoryDeleteException;
import br.com.ecommerce.infra.exception.InventoryNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "usuario", password = "senha", roles = "PAGAMENTOS")
@WebMvcTest(InventoryController.class)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class InventoryControllerTest {

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 quando localizar um inventário por ID válido.")
    void cenario1() throws Exception {
        //given ou arrange
        var inventoryId = 1;
        var inventoryResponse = MockBuilders.getInventoryResponse();

        //when ou act
        when(this.inventoryService.retrieveInventory(inventoryId)).thenReturn(inventoryResponse);

        //then ou assert
        mvc
                .perform(get("/inventory/" + inventoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando receber um ID e não encontrar o inventário.")
    void cenario2() throws Exception {
        //given ou arrange
        var inventoryId = 1;

        //when ou act
        doThrow(InventoryNotFoundException.class)
                .when(this.inventoryService).retrieveInventory(inventoryId);

        //then ou assert
        mvc
                .perform(get("/inventory/" + inventoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando receber um ID inválido (negativo).")
    void cenario3() throws Exception {
        //given ou arrange
        var idInvalido = -1;

        //then ou assert
        mvc
                .perform(get("/inventory/" + idInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 quando localizar uma lista de inventários válidos.")
    void cenario4() throws Exception {
        //given ou arrange
        var inventoryResponseList = MockBuilders.buildListInventoryResponse();

        //when ou act
        when(this.inventoryService.retrieveListInventories()).thenReturn(inventoryResponseList);

        //then ou assert
        mvc
                .perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    @DisplayName("Deve retornar 404 quando buscar lista de inventários e não encontrar nenhum.")
    void cenario5() throws Exception {
        //when ou act
        doThrow(InventoryNotFoundException.class)
                .when(this.inventoryService).retrieveListInventories();

        //then ou assert
        mvc
                .perform(get("/inventory"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 quando atualizar um inventário com sucesso")
    void cenario6() throws Exception {
        //given ou arrange
        var inventoryRequest = MockBuilders.buildInventoryRequest();
        var inventoryUpdated = MockBuilders.getInventoryResponse();

        //when ou act
        when(this.inventoryService.createInventory(inventoryRequest)).thenReturn(inventoryUpdated);

        //then ou assert
        mvc
                .perform(put("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao atualizar um inventário")
    void cenario7() throws Exception {
        //given ou arrange
        var inventoryRequest = MockBuilders.buildInventoryRequest();

        //when ou act
        when(this.inventoryService.createInventory(inventoryRequest))
                .thenThrow(new InventoryCreateException("Error creating inventory", new RuntimeException()));

        //then ou assert
        mvc
                .perform(put("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar atualizar inventário com dados inválidos")
    void cenario8() throws Exception {
        //given ou arrange
        var inventoryRequestInvalido = MockBuilders.buildInventoryRequest();
        inventoryRequestInvalido.setProductCode("123"); // Código inválido

        //then ou assert
        mvc
                .perform(put("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 201 quando criar um inventário com sucesso")
    void cenario9() throws Exception {
        //given ou arrange
        var inventoryRequest = MockBuilders.buildInventoryRequest();
        var inventoryCreated = MockBuilders.getInventoryResponse();

        //when ou act
        when(this.inventoryService.createInventory(inventoryRequest)).thenReturn(inventoryCreated);

        //then ou assert
        mvc
                .perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao criar um inventário")
    void cenario10() throws Exception {
        //given ou arrange
        var inventoryRequest = MockBuilders.buildInventoryRequest();

        //when ou act
        when(this.inventoryService.createInventory(inventoryRequest))
                .thenThrow(new InventoryCreateException("Error creating inventory", new RuntimeException()));

        //then ou assert
        mvc
                .perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar criar inventário com dados inválidos")
    void cenario11() throws Exception {
        //given ou arrange
        var inventoryRequestInvalido = MockBuilders.buildInventoryRequest();
        inventoryRequestInvalido.setAvailableQuantity(null); // Quantidade obrigatória

        //then ou assert
        mvc
                .perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 204 quando deletar um inventário com sucesso")
    void cenario12() throws Exception {
        //given ou arrange
        var inventoryId = 1;

        //when ou act
        doNothing().when(this.inventoryService).deleteInventory(inventoryId);

        //then ou assert
        mvc
                .perform(delete("/inventory/" + inventoryId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao deletar um inventário")
    void cenario13() throws Exception {
        //given ou arrange
        var inventoryId = 1;

        //when ou act
        doThrow(new InventoryDeleteException("Error deleting inventory", new RuntimeException()))
                .when(this.inventoryService).deleteInventory(inventoryId);

        //then ou assert
        mvc
                .perform(delete("/inventory/" + inventoryId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar deletar inventário com ID inválido")
    void cenario14() throws Exception {
        //given ou arrange
        var idInvalido = -1;

        //then ou assert
        mvc
                .perform(delete("/inventory/" + idInvalido))
                .andExpect(status().isBadRequest());
    }
}