package br.com.ecommerce.domain.repository.mysql;

import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoricRepository extends JpaRepository<PaymentHistoricEntity, Integer> {
}
