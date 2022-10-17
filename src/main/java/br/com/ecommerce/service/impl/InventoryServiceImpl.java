package br.com.ecommerce.service.impl;

import br.com.ecommerce.adapter.InventoryEntityToResponseAdapter;
import br.com.ecommerce.adapter.InventoryRequestToInventoryEntityAdapter;
import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.controller.response.exception.InventoryCreateException;
import br.com.ecommerce.controller.response.exception.InventoryDeleteException;
import br.com.ecommerce.controller.response.exception.InventoryNotFoundException;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.domain.repository.postgres.InventoryRepository;
import br.com.ecommerce.controller.response.enumerated.MessageEnum;
import br.com.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public InventoryResponse retrieveInventory(Integer id) throws InventoryNotFoundException {
        InventoryEntity inventoryEntity =
                inventoryRepository.findById(id)
                        .orElseThrow(() -> new InventoryNotFoundException(MessageEnum.INVENTORY_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntity);
    }

    @Override
    public List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException {
        List<InventoryEntity> inventoryEntityList = this.inventoryRepository.findAll();
        if (inventoryEntityList.isEmpty())
            throw new InventoryNotFoundException(MessageEnum.INVENTORY_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return inventoryEntityToResponseAdapter.buildListInventoryResponse(inventoryEntityList);
    }

    @Override
    public InventoryResponse createOrUpdateInventory(InventoryRequest inventoryRequest) throws InventoryCreateException {
        InventoryEntity inventoryEntity = inventoryRequestToInventoryEntityAdapter.getInventoryEntity(inventoryRequest);
        try {
            InventoryEntity inventoryEntitySaved = this.inventoryRepository.save(inventoryEntity);
            return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntitySaved);
        } catch (Exception exception) {
            throw new InventoryCreateException(MessageEnum.INVENTORY_ERROR_ON_CREATE_EXCEPTION.getValue(), exception);
        }
    }

    @Override
    public void deleteInventory(Integer id) throws InventoryDeleteException {
        try {
            this.inventoryRepository.deleteById(id);
        } catch (Exception exception) {
            throw new InventoryDeleteException(MessageEnum.INVENTORY_ERROR_ON_DELETE_EXCEPTION.getValue(), exception);
        }
    }
}