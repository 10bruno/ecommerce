package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.request.ProductRequest;
import br.com.jesus.lojavirtual.domain.entity.postgres.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestToProductEntityAdapter {
    public ProductEntity getProductEntity(ProductRequest productRequest) {
        return new ProductEntity(productRequest.getCode(), productRequest.getCategory(), productRequest.getTitle(), productRequest.getDescription(), productRequest.getWeight(), productRequest.getDateRegister());
    }
}
