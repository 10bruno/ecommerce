package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.request.InventoryRequest;
import br.com.jesus.lojavirtual.controller.request.ProductRequest;
import br.com.jesus.lojavirtual.domain.entity.postgres.InventoryEntity;
import br.com.jesus.lojavirtual.domain.entity.postgres.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryRequestToInventoryEntityAdapter {
    public InventoryEntity getInventoryEntity(InventoryRequest inventoryRequest) {
        return new InventoryEntity(inventoryRequest.getId(), inventoryRequest.getAvailableQuantity(), inventoryRequest.getReservedQuantity(), inventoryRequest.getProductCode());
    }
}
