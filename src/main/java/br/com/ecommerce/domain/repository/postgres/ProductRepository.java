package br.com.ecommerce.domain.repository.postgres;

import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}