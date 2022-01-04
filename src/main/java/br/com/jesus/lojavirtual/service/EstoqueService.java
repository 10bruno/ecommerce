package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.domain.entity.postgres.Estoque;

import java.util.List;
import java.util.Optional;

public interface EstoqueService {
    Optional<Estoque> recuperaUmItem(String id);

    List<Estoque> recuperaListaItens();

    Estoque criaAtualizaEstoque(Estoque estoque);

    void deletaEstoque(String id);
}
