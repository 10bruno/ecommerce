package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.response.ProductResponse;
import br.com.jesus.lojavirtual.domain.entity.postgres.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEntityToResponseAdapter {
    public ProductResponse getProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .code(productEntity.getCode())
                .category(productEntity.getCategory())
                .title(productEntity.getTitle())
                .description(productEntity.getDescription())
                .weight(productEntity.getWeight())
                .dateRegister(productEntity.getDateRegister())
                .build();
    }

    public List<ProductResponse> buildListProductResponse(List<ProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(this::getProductResponse)
                .toList();
    }
}