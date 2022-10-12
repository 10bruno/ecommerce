package br.com.ecommerce.service;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.controller.response.exception.InventoryCreateException;
import br.com.ecommerce.controller.response.exception.InventoryNotFoundException;

import java.util.List;

public interface InventoryService {
    InventoryResponse retrieveInventory(String id) throws InventoryNotFoundException;

    List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException;

    InventoryResponse createOrUpdateInventory(InventoryRequest inventoryRequest) throws InventoryCreateException;

    void deleteInventory(String id);
}
