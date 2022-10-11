package br.com.jesus.lojavirtual.domain.repository.postgres;

import br.com.jesus.lojavirtual.domain.entity.postgres.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}