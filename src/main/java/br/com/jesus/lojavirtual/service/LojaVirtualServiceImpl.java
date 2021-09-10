package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.entity.Produto;
import br.com.jesus.lojavirtual.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LojaVirtualServiceImpl implements LojaVirtualService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public LojaVirtualServiceImpl(ProdutoRepository produtoRepository) {
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
    public Produto atualizaProduto(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Override
    public Produto novoProduto(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Override
    public void deletaProduto(String id) {
        this.produtoRepository.deleteById(id);
    }
}
