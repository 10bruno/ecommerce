package br.com.ecommerce.controller.impl;

import br.com.ecommerce.controller.ProductController;
import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.domain.service.ProductService;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = ControllerConstant.PRODUCT, description = "CRUD of the product service.")
@Slf4j
@Validated
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Autowired
    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductResponse> retrieveProduct(String id) throws ProductNotFoundException {
        log.info("GET - Searching for a specific product id {}.", id);
        ProductResponse returnProduct = this.productService.retrieveProduct(id);

        return ResponseEntity.ok(returnProduct);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> retrieveListProducts() throws ProductNotFoundException {
        log.info("GET - Searching all products.");
        List<ProductResponse> listProducts = this.productService.retrieveListProducts();

        return ResponseEntity.ok(listProducts);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody
                                                         @Valid
                                                         ProductRequest productRequest) throws ProductCreateException {
        log.info("PUT - Update product information {}.", productRequest);
        ProductResponse returnProduct = this.productService.createProduct(productRequest);

        return ResponseEntity.ok(returnProduct);
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(@RequestBody
                                                         @Valid
                                                         ProductRequest productRequest,
                                                         UriComponentsBuilder uriBuilder) throws ProductCreateException {
        log.info("POST - Create a product {}.", productRequest);
        ProductResponse returnProduct = this.productService.createProduct(productRequest);

        var uri = uriBuilder.path("/product/{id}").buildAndExpand(returnProduct.getCode()).toUri();

        return ResponseEntity.created(uri).body(returnProduct);
    }

    @Override
    public ResponseEntity<String> deleteProduct(String id) throws ProductDeleteException {
        log.info("DELETE - Delete a product {}.", id);
        this.productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
