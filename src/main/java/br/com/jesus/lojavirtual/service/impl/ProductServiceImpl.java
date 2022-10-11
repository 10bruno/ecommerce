package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.adapter.ProductEntityToResponseAdapter;
import br.com.jesus.lojavirtual.adapter.ProductRequestToProductEntityAdapter;
import br.com.jesus.lojavirtual.controller.request.ProductRequest;
import br.com.jesus.lojavirtual.controller.response.ProductResponse;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerNotFoundException;
import br.com.jesus.lojavirtual.controller.response.exception.ProductCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.ProductNotFoundException;
import br.com.jesus.lojavirtual.domain.entity.postgres.ProductEntity;
import br.com.jesus.lojavirtual.domain.repository.postgres.ProductRepository;
import br.com.jesus.lojavirtual.enumerated.MessageEnum;
import br.com.jesus.lojavirtual.service.ProductService;
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
