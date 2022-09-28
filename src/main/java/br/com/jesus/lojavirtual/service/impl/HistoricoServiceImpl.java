package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.mysql.domain.Historico;
import br.com.jesus.lojavirtual.mysql.repository.HistoricoRepository;
import br.com.jesus.lojavirtual.service.HistoricoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Optional<Historico> recuperaUmHistorico(Integer id) {
        return this.historicoRepository.findById(id);
    }

    @Override
    public List<Historico> recuperaListaHistoricos() {
        return this.historicoRepository.findAll();
    }

    @Override
    public Historico criaAtualizaHistorico(Historico historico) {
        historico.setData(LocalDate.now());
        return this.historicoRepository.save(historico);
    }

    @Override
    public void deletaHistorico(Integer id) {
        this.historicoRepository.deleteById(id);
    }
}
