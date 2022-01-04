package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.domain.entity.postgres.Cliente;
import br.com.jesus.lojavirtual.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojavirtual/cliente")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{cpf}")
    public Optional<Cliente> getUmCliente(@PathVariable String cpf) {
        log.info("GET - Pesquisando um cliente especifico {}", cpf);
        return this.clienteService.recuperaUmCliente(cpf);
    }

    @GetMapping()
    public List<Cliente> getListaClientes() {
        log.info("GET - Pesquisando uma lista de todos os clientes");
        return this.clienteService.recuperaListaClientes();
    }

    @PutMapping()
    public Cliente putUmCliente(@RequestBody Cliente cliente) {
        log.info("PUT - Altera um ou alguns elementos de um cliente especifico {}", cliente);
        return this.clienteService.criaAtualizaCliente(cliente);
    }

    @PostMapping()
    public Cliente postNovoCliente(@RequestBody Cliente cliente) {
        log.info("POST - Cria um novo cliente {}", cliente);
        return this.clienteService.criaAtualizaCliente(cliente);
    }

    @DeleteMapping("/{cpf}")
    public void deleteUmCliente(@PathVariable String cpf) {
        log.info("DELETE - Deleta um cliente {}", cpf);
        this.clienteService.deletaCliente(cpf);
    }

}
