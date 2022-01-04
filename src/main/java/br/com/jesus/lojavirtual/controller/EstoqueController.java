package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.domain.entity.postgres.Estoque;
import br.com.jesus.lojavirtual.service.EstoqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojavirtual/estoque")
@Slf4j
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{id}")
    public Optional<Estoque> getUmEstoque(@PathVariable String id) {
        log.info("GET - Pesquisando um estoque especifico {}", id);
        return this.estoqueService.recuperaUmItem(id);
    }

    @GetMapping()
    public List<Estoque> getListaEstoques() {
        log.info("GET - Pesquisando uma lista de todos os estoques");
        return this.estoqueService.recuperaListaItens();
    }

    @PutMapping()
    public Estoque putUmEstoque(@RequestBody Estoque estoque) {
        log.info("PUT - Altera um ou alguns elementos de um estoque especifico {}", estoque);
        return this.estoqueService.criaAtualizaEstoque(estoque);
    }

    @PostMapping()
    public Estoque postNovoEstoque(@RequestBody Estoque estoque) {
        log.info("POST - Cria um novo estoque {}", estoque);
        return this.estoqueService.criaAtualizaEstoque(estoque);
    }

    @DeleteMapping("/{id}")
    public void deleteUmEstoque(@PathVariable String id) {
        log.info("DELETE - Deleta um estoque {}", id);
        this.estoqueService.deletaEstoque(id);
    }

}
