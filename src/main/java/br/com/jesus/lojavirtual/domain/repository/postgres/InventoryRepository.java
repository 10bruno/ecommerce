package br.com.jesus.lojavirtual.domain.repository.postgres;

import br.com.jesus.lojavirtual.domain.entity.postgres.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
}
