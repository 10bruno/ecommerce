package br.com.ecommerce.domain.repository.mysql;

import br.com.ecommerce.domain.entity.mysql.HistoricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricRepository extends JpaRepository<HistoricEntity, Integer> {
}
