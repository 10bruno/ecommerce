package br.com.ecommerce.adapter;

import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryEntityToResponseAdapter {
    public InventoryResponse getInventoryResponse(InventoryEntity inventoryEntity) {
        return InventoryResponse.builder()
                .id(inventoryEntity.getId())
                .availableQuantity(inventoryEntity.getAvailableQuantity())
                .reservedQuantity(inventoryEntity.getReservedQuantity())
                .productCode(inventoryEntity.getProductCode())
                .build();
    }

    public List<InventoryResponse> buildListInventoryResponse(List<InventoryEntity> inventoryEntityList) {
        return inventoryEntityList.stream()
                .map(this::getInventoryResponse)
                .toList();
    }
}