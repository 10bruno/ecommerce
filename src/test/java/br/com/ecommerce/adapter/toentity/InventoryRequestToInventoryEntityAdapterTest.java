package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryRequestToInventoryEntityAdapterTest {

    @InjectMocks
    private InventoryRequestToInventoryEntityAdapter adapter;

    @Test
    void shouldConvertInventoryRequestToInventoryEntity_whenValidRequest() {
        InventoryRequest inventoryRequest = MockBuilders.buildInventoryRequest();

        InventoryEntity result = adapter.getInventoryEntity(inventoryRequest);

        assertNotNull(result);
        assertEquals(TestConstants.ID_1, result.getId());
        assertEquals(new BigDecimal(TestConstants.INVENTORY_AVAILABLE_QUANTITY), result.getAvailableQuantity());
        assertEquals(new BigDecimal(TestConstants.INVENTORY_RESERVED_QUANTITY), result.getReservedQuantity());
        assertEquals(TestConstants.PRODUCT_CODE, result.getProductCode());
    }

    @Test
    void shouldHandleNullValues_whenRequestHasNulls() {
        InventoryRequest inventoryRequest = InventoryRequest.builder()
                .id(null)
                .availableQuantity(null)
                .reservedQuantity(null)
                .productCode(null)
                .build();

        InventoryEntity result = adapter.getInventoryEntity(inventoryRequest);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getAvailableQuantity());
        assertNull(result.getReservedQuantity());
        assertNull(result.getProductCode());
    }
}