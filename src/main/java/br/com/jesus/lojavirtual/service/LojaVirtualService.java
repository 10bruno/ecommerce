package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface LojaVirtualService {
    Optional<Produto> recuperaUmProduto(String id);

    List<Produto> recuperaListaProdutos();

    Produto atualizaProduto(Produto produto);

    Produto novoProduto(Produto produto);

    void deletaProduto(String id);
}
