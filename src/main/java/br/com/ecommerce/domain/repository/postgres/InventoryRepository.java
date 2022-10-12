package br.com.ecommerce.domain.repository.postgres;

import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
}
