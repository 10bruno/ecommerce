package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.controller.request.ProductRequest;
import br.com.jesus.lojavirtual.controller.response.ProductResponse;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerNotFoundException;
import br.com.jesus.lojavirtual.controller.response.exception.ProductCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductResponse retrieveProduct(String id) throws ProductNotFoundException;

    List<ProductResponse> retrieveListProducts() throws CustomerNotFoundException;

    ProductResponse createOrUpdateProduct(ProductRequest productRequest) throws ProductCreateException;

    void deleteProduct(String id);
}
