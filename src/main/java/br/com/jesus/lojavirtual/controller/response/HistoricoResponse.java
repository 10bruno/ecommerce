package br.com.jesus.lojavirtual.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoricoResponse {
    private Integer id;
    private String descricao;
    private String tipo;
    private LocalDate data;
}
