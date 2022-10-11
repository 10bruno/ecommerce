package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.adapter.InventoryEntityToResponseAdapter;
import br.com.jesus.lojavirtual.adapter.InventoryRequestToInventoryEntityAdapter;
import br.com.jesus.lojavirtual.controller.request.InventoryRequest;
import br.com.jesus.lojavirtual.controller.response.InventoryResponse;
import br.com.jesus.lojavirtual.controller.response.exception.InventoryCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.InventoryNotFoundException;
import br.com.jesus.lojavirtual.domain.entity.postgres.InventoryEntity;
import br.com.jesus.lojavirtual.domain.repository.postgres.InventoryRepository;
import br.com.jesus.lojavirtual.enumerated.MessageEnum;
import br.com.jesus.lojavirtual.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryRequestToInventoryEntityAdapter inventoryRequestToInventoryEntityAdapter;
    private final InventoryEntityToResponseAdapter inventoryEntityToResponseAdapter;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryRequestToInventoryEntityAdapter inventoryRequestToInventoryEntityAdapter, InventoryEntityToResponseAdapter inventoryEntityToResponseAdapter) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryRequestToInventoryEntityAdapter = inventoryRequestToInventoryEntityAdapter;
        this.inventoryEntityToResponseAdapter = inventoryEntityToResponseAdapter;
    }

    @Override
    public InventoryResponse retrieveInventory(String id) throws InventoryNotFoundException {
        InventoryEntity inventoryEntity =
                inventoryRepository.findById(id)
                        .orElseThrow(() -> new InventoryNotFoundException(MessageEnum.INVENTORY_NOT_FOUND_EXCEPTION.getValue()));

        return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntity);
    }

    @Override
    public List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException {
        List<InventoryEntity> inventoryEntityList =
                Optional.of(this.inventoryRepository.findAll())
                        .orElseThrow(() -> new InventoryNotFoundException(MessageEnum.INVENTORY_NOT_FOUND_EXCEPTION.getValue()));

        return inventoryEntityToResponseAdapter.buildListInventoryResponse(inventoryEntityList);
    }

    @Override
    public InventoryResponse createOrUpdateInventory(InventoryRequest inventoryRequest) throws InventoryCreateException {
        InventoryEntity inventoryEntity = inventoryRequestToInventoryEntityAdapter.getInventoryEntity(inventoryRequest);

        InventoryEntity inventoryEntitySaved =
                Optional.of(this.inventoryRepository.save(inventoryEntity))
                        .orElseThrow(() -> new InventoryCreateException(MessageEnum.INVENTORY_ERROR_ON_CREATE_EXCEPTION.getValue()));

        return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntitySaved);
    }

    @Override
    public void deleteInventory(String id) {
        this.inventoryRepository.deleteById(id);
    }
}
