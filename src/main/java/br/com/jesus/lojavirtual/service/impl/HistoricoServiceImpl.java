package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.domain.entity.mysql.Historico;
import br.com.jesus.lojavirtual.repository.mysql.HistoricoRepository;
import br.com.jesus.lojavirtual.service.HistoricoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HistoricoServiceImpl implements HistoricoService {

    private final HistoricoRepository historicoRepository;

    @Autowired
    public HistoricoServiceImpl(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @Override
    public Optional<Historico> recuperaUmHistorico(String id) {
        return this.historicoRepository.findById(id);
    }

    @Override
    public List<Historico> recuperaListaHistoricos() {
        return this.historicoRepository.findAll();
    }

    @Override
    public Historico criaAtualizaHistorico(Historico historico) {
        return this.historicoRepository.save(historico);
    }

    @Override
    public void deletaHistorico(String id) {
        this.historicoRepository.deleteById(id
        );
    }
}
