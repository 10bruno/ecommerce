package br.com.ecommerce.service;

import br.com.ecommerce.adapter.toentity.ProductRequestToProductEntityAdapter;
import br.com.ecommerce.adapter.toresponse.ProductEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.domain.repository.postgres.ProductRepository;
import br.com.ecommerce.domain.service.impl.ProductServiceImpl;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
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
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRequestToProductEntityAdapter productRequestToProductEntityAdapter;

    @Mock
    private ProductEntityToResponseAdapter productEntityToResponseAdapter;

    @Test
    void shouldReturnProductResponse_whenRetrieveProductSuccess() throws ProductNotFoundException {
        ProductEntity productEntity = MockBuilders.getProductEntity();
        ProductResponse productResponse = MockBuilders.getProductResponse();

        when(productRepository.findById(TestConstants.PRODUCT_CODE)).thenReturn(Optional.of(productEntity));
        when(productEntityToResponseAdapter.getProductResponse(productEntity)).thenReturn(productResponse);

        ProductResponse result = productService.retrieveProduct(TestConstants.PRODUCT_CODE);

        assertNotNull(result);
        assertEquals(productResponse, result);
        verify(productRepository, times(1)).findById(TestConstants.PRODUCT_CODE);
        verify(productEntityToResponseAdapter, times(1)).getProductResponse(productEntity);
    }

    @Test
    void shouldThrowProductNotFoundException_whenProductNotFound() {
        when(productRepository.findById(TestConstants.PRODUCT_CODE)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, 
            () -> productService.retrieveProduct(TestConstants.PRODUCT_CODE));

        assertEquals(MessageEnum.PRODUCT_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(productRepository, times(1)).findById(TestConstants.PRODUCT_CODE);
        verify(productEntityToResponseAdapter, never()).getProductResponse(any());
    }

    @Test
    void shouldReturnProductList_whenRetrieveListProductsSuccess() throws ProductNotFoundException {
        List<ProductEntity> productEntityList = List.of(new ProductEntity(), new ProductEntity());
        List<ProductResponse> productResponseList = MockBuilders.buildListProductResponse();

        when(productRepository.findAll()).thenReturn(productEntityList);
        when(productEntityToResponseAdapter.buildListProductResponse(productEntityList)).thenReturn(productResponseList);

        List<ProductResponse> result = productService.retrieveListProducts();

        assertNotNull(result);
        assertEquals(productResponseList, result);
        verify(productRepository, times(1)).findAll();
        verify(productEntityToResponseAdapter, times(1)).buildListProductResponse(productEntityList);
    }

    @Test
    void shouldThrowProductNotFoundException_whenProductListEmpty() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, 
            () -> productService.retrieveListProducts());

        assertEquals(MessageEnum.PRODUCT_LIST_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(productRepository, times(1)).findAll();
        verify(productEntityToResponseAdapter, never()).buildListProductResponse(any());
    }

    @Test
    void shouldCreateProduct_whenCreateProductSuccess() throws ProductCreateException {
        ProductRequest productRequest = MockBuilders.buildProductRequest();
        ProductEntity productEntity = new ProductEntity();
        ProductEntity savedProductEntity = new ProductEntity();
        ProductResponse productResponse = MockBuilders.getProductResponse();

        when(productRequestToProductEntityAdapter.getProductEntity(productRequest)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(savedProductEntity);
        when(productEntityToResponseAdapter.getProductResponse(savedProductEntity)).thenReturn(productResponse);

        ProductResponse result = productService.createProduct(productRequest);

        assertNotNull(result);
        assertEquals(productResponse, result);
        verify(productRequestToProductEntityAdapter, times(1)).getProductEntity(productRequest);
        verify(productRepository, times(1)).save(productEntity);
        verify(productEntityToResponseAdapter, times(1)).getProductResponse(savedProductEntity);
    }

    @Test
    void shouldThrowProductCreateException_whenCreateProductFails() {
        ProductRequest productRequest = MockBuilders.buildProductRequest();
        ProductEntity productEntity = new ProductEntity();
        Exception repositoryException = new RuntimeException("Database error");

        when(productRequestToProductEntityAdapter.getProductEntity(productRequest)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenThrow(repositoryException);

        ProductCreateException exception = assertThrows(ProductCreateException.class, 
            () -> productService.createProduct(productRequest));

        assertEquals(MessageEnum.PRODUCT_ERROR_ON_CREATE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(productRequestToProductEntityAdapter, times(1)).getProductEntity(productRequest);
        verify(productRepository, times(1)).save(productEntity);
        verify(productEntityToResponseAdapter, never()).getProductResponse(any());
    }

    @Test
    void shouldDeleteProduct_whenDeleteProductSuccess() throws ProductDeleteException {
        doNothing().when(productRepository).deleteById(TestConstants.PRODUCT_CODE);

        assertDoesNotThrow(() -> productService.deleteProduct(TestConstants.PRODUCT_CODE));
        
        verify(productRepository, times(1)).deleteById(TestConstants.PRODUCT_CODE);
    }

    @Test
    void shouldThrowProductDeleteException_whenDeleteProductFails() {
        Exception repositoryException = new RuntimeException("Database error");
        doThrow(repositoryException).when(productRepository).deleteById(TestConstants.PRODUCT_CODE);

        ProductDeleteException exception = assertThrows(ProductDeleteException.class, 
            () -> productService.deleteProduct(TestConstants.PRODUCT_CODE));

        assertEquals(MessageEnum.PRODUCT_ERROR_ON_DELETE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(productRepository, times(1)).deleteById(TestConstants.PRODUCT_CODE);
    }
}