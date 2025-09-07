package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductEntityToResponseAdapterTest {

    @InjectMocks
    private ProductEntityToResponseAdapter adapter;

    @Test
    void shouldConvertProductEntityToProductResponse_whenValidEntity() {
        ProductEntity productEntity = MockBuilders.getProductEntity();

        ProductResponse result = adapter.getProductResponse(productEntity);

        assertNotNull(result);
        assertEquals(TestConstants.PRODUCT_CODE, result.getCode());
        assertEquals(TestConstants.PRODUCT_CATEGORY, result.getCategory());
        assertEquals(TestConstants.PRODUCT_TITLE, result.getTitle());
        assertEquals(TestConstants.PRODUCT_DESCRIPTION, result.getDescription());
        assertEquals(new BigDecimal(TestConstants.PRODUCT_WEIGHT), result.getWeight());
        assertNotNull(result.getDateRegister());
    }

    @Test
    void shouldHandleNullValues_whenEntityHasNulls() {
        ProductEntity productEntity = new ProductEntity(null, null, null, null, null, null);

        ProductResponse result = adapter.getProductResponse(productEntity);

        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getCategory());
        assertNull(result.getTitle());
        assertNull(result.getDescription());
        assertNull(result.getWeight());
        assertNull(result.getDateRegister());
    }

    @Test
    void shouldConvertListOfProductEntities_whenValidList() {
        List<ProductEntity> productEntityList = List.of(
            MockBuilders.getProductEntity(),
            new ProductEntity("CD5678", "Books", "Test Book", "Description", new BigDecimal("2.50"), LocalDate.now())
        );

        List<ProductResponse> result = adapter.buildListProductResponse(productEntityList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TestConstants.PRODUCT_CODE, result.get(0).getCode());
        assertEquals("CD5678", result.get(1).getCode());
    }

    @Test
    void shouldReturnEmptyList_whenEntityListEmpty() {
        List<ProductEntity> productEntityList = List.of();

        List<ProductResponse> result = adapter.buildListProductResponse(productEntityList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}