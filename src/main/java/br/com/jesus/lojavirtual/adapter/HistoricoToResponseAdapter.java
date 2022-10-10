package br.com.jesus.lojavirtual.adapter;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HistoricoToResponseAdapter {
    private String descricao;
    private String tipo;
    private LocalDate data;
}
