package br.com.ecommerce.adapter;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryRequestToInventoryEntityAdapter {
    public InventoryEntity getInventoryEntity(InventoryRequest inventoryRequest) {
        return new InventoryEntity(inventoryRequest.getId(), inventoryRequest.getAvailableQuantity(), inventoryRequest.getReservedQuantity(), inventoryRequest.getProductCode());
    }
}
