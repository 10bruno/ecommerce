package br.com.ecommerce.controller;

import br.com.ecommerce.controller.impl.ProductControllerImpl;
import br.com.ecommerce.domain.service.ProductService;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
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

@WebMvcTest(ProductControllerImpl.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 quando localizar um produto por código válido.")
    void cenario1() throws Exception {
        //given ou arrange
        var productCode = "AB1234";
        var productResponse = MockBuilders.getProductResponse();

        //when ou act
        when(this.productService.retrieveProduct(productCode)).thenReturn(productResponse);

        //then ou assert
        mvc
                .perform(get("/product/" + productCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando receber um código e não encontrar o produto.")
    void cenario2() throws Exception {
        //given ou arrange
        var productCode = "AB1234";

        //when ou act
        doThrow(ProductNotFoundException.class)
                .when(this.productService).retrieveProduct(productCode);

        //then ou assert
        mvc
                .perform(get("/product/" + productCode))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando receber um código com formato inválido.")
    void cenario3() throws Exception {
        //given ou arrange
        var codigoInvalido = "123";

        //then ou assert
        mvc
                .perform(get("/product/" + codigoInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 quando localizar uma lista de produtos válidos.")
    void cenario4() throws Exception {
        //given ou arrange
        var productResponseList = MockBuilders.buildListProductResponse();

        //when ou act
        when(this.productService.retrieveListProducts()).thenReturn(productResponseList);

        //then ou assert
        mvc
                .perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    @DisplayName("Deve retornar 404 quando buscar lista de produtos e não encontrar nenhum.")
    void cenario5() throws Exception {
        //when ou act
        doThrow(ProductNotFoundException.class)
                .when(this.productService).retrieveListProducts();

        //then ou assert
        mvc
                .perform(get("/product"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 quando atualizar um produto com sucesso")
    void cenario6() throws Exception {
        //given ou arrange
        var productRequest = MockBuilders.buildProductRequest();
        var productUpdated = MockBuilders.getProductResponse();

        //when ou act
        when(this.productService.createProduct(productRequest)).thenReturn(productUpdated);

        //then ou assert
        mvc
                .perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao atualizar um produto")
    void cenario7() throws Exception {
        //given ou arrange
        var productRequest = MockBuilders.buildProductRequest();

        //when ou act
        when(this.productService.createProduct(productRequest))
                .thenThrow(new ProductCreateException("Error creating product", new RuntimeException()));

        //then ou assert
        mvc
                .perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar atualizar produto com dados inválidos")
    void cenario8() throws Exception {
        //given ou arrange
        var productRequestInvalido = MockBuilders.buildProductRequest();
        productRequestInvalido.setCode("123"); // Código inválido

        //then ou assert
        mvc
                .perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 201 quando criar um produto com sucesso")
    void cenario9() throws Exception {
        //given ou arrange
        var productRequest = MockBuilders.buildProductRequest();
        var productCreated = MockBuilders.getProductResponse();

        //when ou act
        when(this.productService.createProduct(productRequest)).thenReturn(productCreated);

        //then ou assert
        mvc
                .perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao criar um produto")
    void cenario10() throws Exception {
        //given ou arrange
        var productRequest = MockBuilders.buildProductRequest();

        //when ou act
        when(this.productService.createProduct(productRequest))
                .thenThrow(new ProductCreateException("Error creating product", new RuntimeException()));

        //then ou assert
        mvc
                .perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar criar produto com dados inválidos")
    void cenario11() throws Exception {
        //given ou arrange
        var productRequestInvalido = MockBuilders.buildProductRequest();
        productRequestInvalido.setCategory(""); // Categoria obrigatória

        //then ou assert
        mvc
                .perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 204 quando deletar um produto com sucesso")
    void cenario12() throws Exception {
        //given ou arrange
        var productCode = "AB1234";

        //when ou act
        doNothing().when(this.productService).deleteProduct(productCode);

        //then ou assert
        mvc
                .perform(delete("/product/" + productCode))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 500 quando falhar ao deletar um produto")
    void cenario13() throws Exception {
        //given ou arrange
        var productCode = "AB1234";

        //when ou act
        doThrow(new ProductDeleteException("Error deleting product", new RuntimeException()))
                .when(this.productService).deleteProduct(productCode);

        //then ou assert
        mvc
                .perform(delete("/product/" + productCode))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando tentar deletar produto com código inválido")
    void cenario14() throws Exception {
        //given ou arrange
        var codigoInvalido = "123";

        //then ou assert
        mvc
                .perform(delete("/product/" + codigoInvalido))
                .andExpect(status().isBadRequest());
    }
}