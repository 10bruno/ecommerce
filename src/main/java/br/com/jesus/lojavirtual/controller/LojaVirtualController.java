package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.entity.Produto;
import br.com.jesus.lojavirtual.service.LojaVirtualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojavirtual")
@Slf4j
public class LojaVirtualController {

    private final LojaVirtualService lojaVirtualService;

    @Autowired
    public LojaVirtualController(LojaVirtualService lojaVirtualService) {
        this.lojaVirtualService = lojaVirtualService;
    }

    @GetMapping("/produto/{id}")
    public Optional<Produto> getUmProduto(@PathVariable String id) {
        log.info("GET - Pesquisando um produto especifico {}", id);
        return this.lojaVirtualService.recuperaUmProduto(id);
    }

    @GetMapping("/produto")
    public List<Produto> getListaProdutos() {
        log.info("GET - Pesquisando uma lista de todos os produtos");
        return this.lojaVirtualService.recuperaListaProdutos();
    }

    @PutMapping("/produto/")
    public Produto putUmProduto(@RequestBody Produto produto) {
        log.info("PUT - Altera um ou alguns elementos de um produto especifico {}", produto);
        return this.lojaVirtualService.atualizaProduto(produto);
    }

    @PostMapping("/produto")
    public Produto postNovoProduto(@RequestBody Produto produto) {
        log.info("POST - Cria um novo produto {}", produto);
        return this.lojaVirtualService.novoProduto(produto);
    }

    @DeleteMapping("/produto/{id}")
    public void deleteUmProduto(@PathVariable String id) {
        log.info("DELETE - Deleta um produto {}", id);
        this.lojaVirtualService.deletaProduto(id);
    }

}
