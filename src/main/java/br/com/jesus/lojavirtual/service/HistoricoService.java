package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.domain.entity.mysql.Historico;

import java.util.List;
import java.util.Optional;

public interface HistoricoService {
    Optional<Historico> recuperaUmHistorico(String id);

    List<Historico> recuperaListaHistoricos();

    Historico criaAtualizaHistorico(Historico historico);

    void deletaHistorico(String id);
}
