package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.mysql.domain.Historico;
import br.com.jesus.lojavirtual.service.HistoricoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojavirtual/historico")
@Slf4j
public class HistoricoController {

    private final HistoricoService historicoService;

    @Autowired
    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @GetMapping("/{id}")
    public Optional<Historico> getUmHistorico(@PathVariable String id) {
        log.info("GET - Pesquisando um historico especifico {}", id);
        return this.historicoService.recuperaUmHistorico(id);
    }

    @GetMapping()
    public List<Historico> getListaHistoricos() {
        log.info("GET - Pesquisando uma lista de todos os historicos");
        return this.historicoService.recuperaListaHistoricos();
    }

    @PutMapping()
    public Historico putUmHistorico(@RequestBody Historico historico) {
        log.info("PUT - Altera um ou alguns elementos de um historico especifico {}", historico);
        return this.historicoService.criaAtualizaHistorico(historico);
    }

    @PostMapping()
    public Historico postNovoHistorico(@RequestBody Historico historico) {
        log.info("POST - Cria um novo historico {}", historico);
        return this.historicoService.criaAtualizaHistorico(historico);
    }

    @DeleteMapping("/{id}")
    public void deleteUmHistorico(@PathVariable String id) {
        log.info("DELETE - Deleta um historico {}", id);
        this.historicoService.deletaHistorico(id);
    }

}
