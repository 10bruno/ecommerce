package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRequestToProductEntityAdapterTest {

    @InjectMocks
    private ProductRequestToProductEntityAdapter adapter;

    @Test
    void shouldConvertProductRequestToProductEntity_whenValidRequest() {
        ProductRequest productRequest = MockBuilders.buildProductRequest();

        ProductEntity result = adapter.getProductEntity(productRequest);

        assertNotNull(result);
        assertEquals(TestConstants.PRODUCT_CODE, result.getCode());
        assertEquals(TestConstants.PRODUCT_CATEGORY, result.getCategory());
        assertEquals(TestConstants.PRODUCT_TITLE, result.getTitle());
        assertEquals(TestConstants.PRODUCT_DESCRIPTION, result.getDescription());
        assertEquals(new BigDecimal(TestConstants.PRODUCT_WEIGHT), result.getWeight());
        assertNotNull(result.getDateRegister());
    }

    @Test
    void shouldHandleNullValues_whenRequestHasNulls() {
        ProductRequest productRequest = ProductRequest.builder()
                .code(null)
                .category(null)
                .title(null)
                .description(null)
                .weight(null)
                .dateRegister(null)
                .build();

        ProductEntity result = adapter.getProductEntity(productRequest);

        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getCategory());
        assertNull(result.getTitle());
        assertNull(result.getDescription());
        assertNull(result.getWeight());
        assertNull(result.getDateRegister());
    }
}