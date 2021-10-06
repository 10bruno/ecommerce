package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.postgres.domain.Estoque;
import br.com.jesus.lojavirtual.postgres.repository.EstoqueRepository;
import br.com.jesus.lojavirtual.service.EstoqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public Optional<Estoque> recuperaUmItem(String id) {
        return this.estoqueRepository.findById(id);
    }

    @Override
    public List<Estoque> recuperaListaItens() {
        return this.estoqueRepository.findAll();
    }

    @Override
    public Estoque criaAtualizaEstoque(Estoque estoque) {
        return this.estoqueRepository.save(estoque);
    }

    @Override
    public void deletaEstoque(String id) {
        this.estoqueRepository.deleteById(id);
    }
}
