package br.com.jesus.lojavirtual.postgres.repository;

import br.com.jesus.lojavirtual.postgres.domain.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, String> {
}
