package br.com.ecommerce.domain.service;

import br.com.ecommerce.adapter.toentity.InventoryRequestToInventoryEntityAdapter;
import br.com.ecommerce.adapter.toresponse.InventoryEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.domain.repository.postgres.InventoryRepository;
import br.com.ecommerce.infra.exception.InventoryCreateException;
import br.com.ecommerce.infra.exception.InventoryDeleteException;
import br.com.ecommerce.infra.exception.InventoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryRequestToInventoryEntityAdapter inventoryRequestToInventoryEntityAdapter;
    private final InventoryEntityToResponseAdapter inventoryEntityToResponseAdapter;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, InventoryRequestToInventoryEntityAdapter inventoryRequestToInventoryEntityAdapter, InventoryEntityToResponseAdapter inventoryEntityToResponseAdapter) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryRequestToInventoryEntityAdapter = inventoryRequestToInventoryEntityAdapter;
        this.inventoryEntityToResponseAdapter = inventoryEntityToResponseAdapter;
    }

    public InventoryResponse retrieveInventory(Integer id) throws InventoryNotFoundException {
        InventoryEntity inventoryEntity =
                inventoryRepository.findById(id)
                        .orElseThrow(() -> new InventoryNotFoundException(MessageEnum.INVENTORY_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntity);
    }

    public List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException {
        List<InventoryEntity> inventoryEntityList = this.inventoryRepository.findAll();
        if (inventoryEntityList.isEmpty())
            throw new InventoryNotFoundException(MessageEnum.INVENTORY_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return inventoryEntityToResponseAdapter.buildListInventoryResponse(inventoryEntityList);
    }

    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) throws InventoryCreateException {
        InventoryEntity inventoryEntity = inventoryRequestToInventoryEntityAdapter.getInventoryEntity(inventoryRequest);
        try {
            InventoryEntity inventoryEntitySaved = this.inventoryRepository.save(inventoryEntity);
            return inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntitySaved);
        } catch (Exception exception) {
            throw new InventoryCreateException(MessageEnum.INVENTORY_ERROR_ON_CREATE_EXCEPTION.getValue(), exception);
        }
    }

    @Transactional
    public void deleteInventory(Integer id) throws InventoryDeleteException {
        try {
            this.inventoryRepository.deleteById(id);
        } catch (Exception exception) {
            throw new InventoryDeleteException(MessageEnum.INVENTORY_ERROR_ON_DELETE_EXCEPTION.getValue(), exception);
        }
    }
}