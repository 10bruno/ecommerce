package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.infra.exception.InventoryCreateException;
import br.com.ecommerce.infra.exception.InventoryDeleteException;
import br.com.ecommerce.infra.exception.InventoryNotFoundException;

import java.util.List;

public interface InventoryService {
    InventoryResponse retrieveInventory(Integer id) throws InventoryNotFoundException;

    List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException;

    InventoryResponse createInventory(InventoryRequest inventoryRequest) throws InventoryCreateException;

    void deleteInventory(Integer id) throws InventoryDeleteException;
}
