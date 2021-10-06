package br.com.jesus.lojavirtual.mysql.repository;

import br.com.jesus.lojavirtual.mysql.domain.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, String> {
}
