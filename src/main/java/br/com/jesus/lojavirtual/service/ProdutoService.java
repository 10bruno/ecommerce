package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.domain.entity.postgres.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    Optional<Produto> recuperaUmProduto(String id);

    List<Produto> recuperaListaProdutos();

    Produto criaAtualizaProduto(Produto produto);

    void deletaProduto(String id);
}
