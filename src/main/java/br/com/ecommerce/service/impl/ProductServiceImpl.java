package br.com.ecommerce.service.impl;

import br.com.ecommerce.adapter.ProductEntityToResponseAdapter;
import br.com.ecommerce.adapter.ProductRequestToProductEntityAdapter;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.controller.response.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.response.exception.ProductCreateException;
import br.com.ecommerce.controller.response.exception.ProductNotFoundException;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.domain.repository.postgres.ProductRepository;
import br.com.ecommerce.enumerated.MessageEnum;
import br.com.ecommerce.service.ProductService;
import br.com.ecommerce.controller.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductRequestToProductEntityAdapter productRequestToProductEntityAdapter;
    private final ProductEntityToResponseAdapter productEntityToResponseAdapter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRequestToProductEntityAdapter productRequestToProductEntityAdapter, ProductEntityToResponseAdapter productEntityToResponseAdapter) {
        this.productRepository = productRepository;
        this.productRequestToProductEntityAdapter = productRequestToProductEntityAdapter;
        this.productEntityToResponseAdapter = productEntityToResponseAdapter;
    }

    @Override
    public ProductResponse retrieveProduct(String id) throws ProductNotFoundException {
        ProductEntity productEntity =
                productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException(MessageEnum.PRODUCT_NOT_FOUND_EXCEPTION.getValue()));

        return productEntityToResponseAdapter.getProductResponse(productEntity);
    }

    @Override
    public List<ProductResponse> retrieveListProducts() throws CustomerNotFoundException {
        List<ProductEntity> productEntityList =
                Optional.of(this.productRepository.findAll())
                        .orElseThrow(() -> new CustomerNotFoundException(MessageEnum.PRODUCT_LIST_NOT_FOUND_EXCEPTION.getValue()));

        return productEntityToResponseAdapter.buildListProductResponse(productEntityList);
    }

    @Override
    public ProductResponse createOrUpdateProduct(ProductRequest productRequest) throws ProductCreateException {
        ProductEntity productEntity = productRequestToProductEntityAdapter.getProductEntity(productRequest);

        ProductEntity productEntitySaved =
                Optional.of(this.productRepository.save(productEntity))
                        .orElseThrow(() -> new ProductCreateException(MessageEnum.PRODUCT_ERROR_ON_CREATE_EXCEPTION.getValue()));

        return productEntityToResponseAdapter.getProductResponse(productEntitySaved);
    }

    @Override
    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }
}
