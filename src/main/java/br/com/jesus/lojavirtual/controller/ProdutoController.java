package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.postgres.domain.Produto;
import br.com.jesus.lojavirtual.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojavirtual/produto")
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public Optional<Produto> getUmProduto(@PathVariable String id) {
        log.info("GET - Pesquisando um produto especifico {}", id);
        return this.produtoService.recuperaUmProduto(id);
    }

    @GetMapping()
    public List<Produto> getListaProdutos() {
        log.info("GET - Pesquisando uma lista de todos os produtos");
        return this.produtoService.recuperaListaProdutos();
    }

    @PutMapping()
    public Produto putUmProduto(@RequestBody Produto produto) {
        log.info("PUT - Altera um ou alguns elementos de um produto especifico {}", produto);
        return this.produtoService.criaAtualizaProduto(produto);
    }

    @PostMapping()
    public Produto postNovoProduto(@RequestBody Produto produto) {
        log.info("POST - Cria um novo produto {}", produto);
        return this.produtoService.criaAtualizaProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void deleteUmProduto(@PathVariable String id) {
        log.info("DELETE - Deleta um produto {}", id);
        this.produtoService.deletaProduto(id);
    }

}
