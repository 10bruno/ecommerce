package br.com.ecommerce.domain.service.impl;

import br.com.ecommerce.adapter.toresponse.ProductEntityToResponseAdapter;
import br.com.ecommerce.adapter.toentity.ProductRequestToProductEntityAdapter;
import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.domain.service.ProductService;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import br.com.ecommerce.domain.repository.postgres.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        .orElseThrow(() -> new ProductNotFoundException(MessageEnum.PRODUCT_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return productEntityToResponseAdapter.getProductResponse(productEntity);
    }

    @Override
    public List<ProductResponse> retrieveListProducts() throws ProductNotFoundException {
        List<ProductEntity> productEntityList = this.productRepository.findAll();
        if (productEntityList.isEmpty())
            throw new ProductNotFoundException(MessageEnum.PRODUCT_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return productEntityToResponseAdapter.buildListProductResponse(productEntityList);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) throws ProductCreateException {
        ProductEntity productEntity = productRequestToProductEntityAdapter.getProductEntity(productRequest);
        try {
            ProductEntity productEntitySaved = this.productRepository.save(productEntity);
            return productEntityToResponseAdapter.getProductResponse(productEntitySaved);
        } catch (Exception ex) {
            throw new ProductCreateException(MessageEnum.PRODUCT_ERROR_ON_CREATE_EXCEPTION.getValue(), ex);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(String id) throws ProductDeleteException {
        try {
            this.productRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ProductDeleteException(MessageEnum.PRODUCT_ERROR_ON_DELETE_EXCEPTION.getValue(), ex);
        }
    }
}