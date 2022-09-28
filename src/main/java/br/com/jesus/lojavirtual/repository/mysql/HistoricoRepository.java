package br.com.jesus.lojavirtual.repository.mysql;

import br.com.jesus.lojavirtual.domain.entity.mysql.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer> {
}
