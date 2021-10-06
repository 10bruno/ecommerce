package br.com.jesus.lojavirtual.postgres.repository;

import br.com.jesus.lojavirtual.postgres.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
}