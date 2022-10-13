package br.com.ecommerce.service;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.controller.response.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.response.exception.ProductCreateException;
import br.com.ecommerce.controller.response.exception.ProductDeleteException;
import br.com.ecommerce.controller.response.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductResponse retrieveProduct(Integer id) throws ProductNotFoundException;

    List<ProductResponse> retrieveListProducts() throws CustomerNotFoundException;

    ProductResponse createOrUpdateProduct(ProductRequest productRequest) throws ProductCreateException;

    void deleteProduct(Integer id) throws ProductDeleteException;
}
