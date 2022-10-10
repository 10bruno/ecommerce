package br.com.jesus.lojavirtual.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstoqueResponse {
    private Integer idEstoque;
    private BigDecimal qtdDisponivel;
    private BigDecimal qtdReservado;
    private String codigoProduto;
}
