package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.domain.entity.postgres.Cliente;
import br.com.jesus.lojavirtual.repository.postgres.ClienteRepository;
import br.com.jesus.lojavirtual.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> recuperaUmCliente(String cpf) {
        return this.clienteRepository.findById(cpf);
    }

    @Override
    public List<Cliente> recuperaListaClientes() {
        return this.clienteRepository.findAll();
    }

    @Override
    public Cliente criaAtualizaCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    @Override
    public void deletaCliente(String cpf) {
        this.clienteRepository.deleteById(cpf);
    }
}
