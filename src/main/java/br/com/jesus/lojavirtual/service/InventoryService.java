package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.controller.request.InventoryRequest;
import br.com.jesus.lojavirtual.controller.response.InventoryResponse;
import br.com.jesus.lojavirtual.controller.response.exception.InventoryCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.InventoryNotFoundException;

import java.util.List;

public interface InventoryService {
    InventoryResponse retrieveInventory(String id) throws InventoryNotFoundException;

    List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException;

    InventoryResponse createOrUpdateInventory(InventoryRequest inventoryRequest) throws InventoryCreateException;

    void deleteInventory(String id);
}
