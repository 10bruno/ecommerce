package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.domain.entity.postgres.Produto;
import br.com.jesus.lojavirtual.repository.postgres.ProdutoRepository;
import br.com.jesus.lojavirtual.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Optional<Produto> recuperaUmProduto(String id) {
        return this.produtoRepository.findById(id);
    }

    @Override
    public List<Produto> recuperaListaProdutos() {
        return this.produtoRepository.findAll();
    }

    @Override
    public Produto criaAtualizaProduto(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Override
    public void deletaProduto(String id) {
        this.produtoRepository.deleteById(id);
    }
}
