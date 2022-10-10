package br.com.jesus.lojavirtual.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProdutoResponse {
    private String codigo;
    private String categoria;
    private String titulo;
    private String descricao;
    private BigDecimal peso;
    private LocalDate dtCadastro;
}
