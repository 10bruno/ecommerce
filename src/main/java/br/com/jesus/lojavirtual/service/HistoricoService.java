package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.mysql.domain.Historico;
import br.com.jesus.lojavirtual.postgres.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface HistoricoService {
    Optional<Historico> recuperaUmHistorico(Integer id);

    List<Historico> recuperaListaHistoricos();

    Historico criaAtualizaHistorico(Historico historico);

    void deletaHistorico(Integer id);
}
