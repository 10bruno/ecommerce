package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestToProductEntityAdapter {
    public ProductEntity getProductEntity(ProductRequest productRequest) {
        return new ProductEntity(productRequest.getCode(), productRequest.getCategory(), productRequest.getTitle(), productRequest.getDescription(), productRequest.getWeight(), productRequest.getDateRegister());
    }
}
