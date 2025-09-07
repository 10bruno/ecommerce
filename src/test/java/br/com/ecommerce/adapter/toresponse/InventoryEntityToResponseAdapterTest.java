package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryEntityToResponseAdapterTest {

    @InjectMocks
    private InventoryEntityToResponseAdapter adapter;

    @Test
    void shouldConvertInventoryEntityToInventoryResponse_whenValidEntity() {
        InventoryEntity inventoryEntity = MockBuilders.getInventoryEntity();

        InventoryResponse result = adapter.getInventoryResponse(inventoryEntity);

        assertNotNull(result);
        assertEquals(TestConstants.ID_1, result.getId());
        assertEquals(new BigDecimal(TestConstants.INVENTORY_AVAILABLE_QUANTITY), result.getAvailableQuantity());
        assertEquals(new BigDecimal(TestConstants.INVENTORY_RESERVED_QUANTITY), result.getReservedQuantity());
        assertEquals(TestConstants.PRODUCT_CODE, result.getProductCode());
    }

    @Test
    void shouldHandleNullValues_whenEntityHasNulls() {
        InventoryEntity inventoryEntity = new InventoryEntity(null, null, null, null);

        InventoryResponse result = adapter.getInventoryResponse(inventoryEntity);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getAvailableQuantity());
        assertNull(result.getReservedQuantity());
        assertNull(result.getProductCode());
    }

    @Test
    void shouldConvertListOfInventoryEntities_whenValidList() {
        List<InventoryEntity> inventoryEntityList = List.of(
            MockBuilders.getInventoryEntity(),
            new InventoryEntity(2, new BigDecimal("50"), new BigDecimal("5"), "CD5678")
        );

        List<InventoryResponse> result = adapter.buildListInventoryResponse(inventoryEntityList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TestConstants.ID_1, result.get(0).getId());
        assertEquals(Integer.valueOf(2), result.get(1).getId());
    }

    @Test
    void shouldReturnEmptyList_whenEntityListEmpty() {
        List<InventoryEntity> inventoryEntityList = List.of();

        List<InventoryResponse> result = adapter.buildListInventoryResponse(inventoryEntityList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}