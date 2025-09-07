package br.com.ecommerce.service;

import br.com.ecommerce.adapter.toentity.InventoryRequestToInventoryEntityAdapter;
import br.com.ecommerce.adapter.toresponse.InventoryEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.domain.repository.postgres.InventoryRepository;
import br.com.ecommerce.domain.service.impl.InventoryServiceImpl;
import br.com.ecommerce.infra.exception.InventoryCreateException;
import br.com.ecommerce.infra.exception.InventoryDeleteException;
import br.com.ecommerce.infra.exception.InventoryNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryRequestToInventoryEntityAdapter inventoryRequestToInventoryEntityAdapter;

    @Mock
    private InventoryEntityToResponseAdapter inventoryEntityToResponseAdapter;

    @Test
    void shouldReturnInventoryResponse_whenRetrieveInventorySuccess() throws InventoryNotFoundException {
        InventoryEntity inventoryEntity = MockBuilders.getInventoryEntity();
        InventoryResponse inventoryResponse = MockBuilders.getInventoryResponse();

        when(inventoryRepository.findById(TestConstants.ID_1)).thenReturn(Optional.of(inventoryEntity));
        when(inventoryEntityToResponseAdapter.getInventoryResponse(inventoryEntity)).thenReturn(inventoryResponse);

        InventoryResponse result = inventoryService.retrieveInventory(TestConstants.ID_1);

        assertNotNull(result);
        assertEquals(inventoryResponse, result);
        verify(inventoryRepository, times(1)).findById(TestConstants.ID_1);
        verify(inventoryEntityToResponseAdapter, times(1)).getInventoryResponse(inventoryEntity);
    }

    @Test
    void shouldThrowInventoryNotFoundException_whenInventoryNotFound() {
        when(inventoryRepository.findById(TestConstants.ID_1)).thenReturn(Optional.empty());

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, 
            () -> inventoryService.retrieveInventory(TestConstants.ID_1));

        assertEquals(MessageEnum.INVENTORY_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(inventoryRepository, times(1)).findById(TestConstants.ID_1);
        verify(inventoryEntityToResponseAdapter, never()).getInventoryResponse(any());
    }

    @Test
    void shouldReturnInventoryList_whenRetrieveListInventoriesSuccess() throws InventoryNotFoundException {
        List<InventoryEntity> inventoryEntityList = List.of(new InventoryEntity(), new InventoryEntity());
        List<InventoryResponse> inventoryResponseList = MockBuilders.buildListInventoryResponse();

        when(inventoryRepository.findAll()).thenReturn(inventoryEntityList);
        when(inventoryEntityToResponseAdapter.buildListInventoryResponse(inventoryEntityList)).thenReturn(inventoryResponseList);

        List<InventoryResponse> result = inventoryService.retrieveListInventories();

        assertNotNull(result);
        assertEquals(inventoryResponseList, result);
        verify(inventoryRepository, times(1)).findAll();
        verify(inventoryEntityToResponseAdapter, times(1)).buildListInventoryResponse(inventoryEntityList);
    }

    @Test
    void shouldThrowInventoryNotFoundException_whenInventoryListEmpty() {
        when(inventoryRepository.findAll()).thenReturn(new ArrayList<>());

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, 
            () -> inventoryService.retrieveListInventories());

        assertEquals(MessageEnum.INVENTORY_LIST_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(inventoryRepository, times(1)).findAll();
        verify(inventoryEntityToResponseAdapter, never()).buildListInventoryResponse(any());
    }

    @Test
    void shouldCreateInventory_whenCreateInventorySuccess() throws InventoryCreateException {
        InventoryRequest inventoryRequest = MockBuilders.buildInventoryRequest();
        InventoryEntity inventoryEntity = new InventoryEntity();
        InventoryEntity savedInventoryEntity = new InventoryEntity();
        InventoryResponse inventoryResponse = MockBuilders.getInventoryResponse();

        when(inventoryRequestToInventoryEntityAdapter.getInventoryEntity(inventoryRequest)).thenReturn(inventoryEntity);
        when(inventoryRepository.save(inventoryEntity)).thenReturn(savedInventoryEntity);
        when(inventoryEntityToResponseAdapter.getInventoryResponse(savedInventoryEntity)).thenReturn(inventoryResponse);

        InventoryResponse result = inventoryService.createInventory(inventoryRequest);

        assertNotNull(result);
        assertEquals(inventoryResponse, result);
        verify(inventoryRequestToInventoryEntityAdapter, times(1)).getInventoryEntity(inventoryRequest);
        verify(inventoryRepository, times(1)).save(inventoryEntity);
        verify(inventoryEntityToResponseAdapter, times(1)).getInventoryResponse(savedInventoryEntity);
    }

    @Test
    void shouldThrowInventoryCreateException_whenCreateInventoryFails() {
        InventoryRequest inventoryRequest = MockBuilders.buildInventoryRequest();
        InventoryEntity inventoryEntity = new InventoryEntity();
        Exception repositoryException = new RuntimeException("Database error");

        when(inventoryRequestToInventoryEntityAdapter.getInventoryEntity(inventoryRequest)).thenReturn(inventoryEntity);
        when(inventoryRepository.save(inventoryEntity)).thenThrow(repositoryException);

        InventoryCreateException exception = assertThrows(InventoryCreateException.class, 
            () -> inventoryService.createInventory(inventoryRequest));

        assertEquals(MessageEnum.INVENTORY_ERROR_ON_CREATE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(inventoryRequestToInventoryEntityAdapter, times(1)).getInventoryEntity(inventoryRequest);
        verify(inventoryRepository, times(1)).save(inventoryEntity);
        verify(inventoryEntityToResponseAdapter, never()).getInventoryResponse(any());
    }

    @Test
    void shouldDeleteInventory_whenDeleteInventorySuccess() throws InventoryDeleteException {
        doNothing().when(inventoryRepository).deleteById(TestConstants.ID_1);

        assertDoesNotThrow(() -> inventoryService.deleteInventory(TestConstants.ID_1));
        
        verify(inventoryRepository, times(1)).deleteById(TestConstants.ID_1);
    }

    @Test
    void shouldThrowInventoryDeleteException_whenDeleteInventoryFails() {
        Exception repositoryException = new RuntimeException("Database error");
        doThrow(repositoryException).when(inventoryRepository).deleteById(TestConstants.ID_1);

        InventoryDeleteException exception = assertThrows(InventoryDeleteException.class, 
            () -> inventoryService.deleteInventory(TestConstants.ID_1));

        assertEquals(MessageEnum.INVENTORY_ERROR_ON_DELETE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(inventoryRepository, times(1)).deleteById(TestConstants.ID_1);
    }
}