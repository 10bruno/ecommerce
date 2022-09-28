package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.domain.entity.postgres.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Optional<Cliente> recuperaUmCliente(String cpf);

    List<Cliente> recuperaListaClientes();

    Cliente criaAtualizaCliente(Cliente cliente);

    void deletaCliente(String cpf);
}
