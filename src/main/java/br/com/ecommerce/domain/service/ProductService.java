package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductResponse retrieveProduct(String id) throws ProductNotFoundException;

    List<ProductResponse> retrieveListProducts() throws ProductNotFoundException;

    ProductResponse createProduct(ProductRequest productRequest) throws ProductCreateException;

    void deleteProduct(String id) throws ProductDeleteException;
}
