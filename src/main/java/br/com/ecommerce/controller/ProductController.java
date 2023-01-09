package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import br.com.ecommerce.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = ControllerConstant.PRODUCT, description = "CRUD of the product service.")
@Slf4j
@Validated
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Retrieve a product by id",
            tags = {ControllerConstant.PRODUCT},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ProductResponse.class
                                    ))),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )))
            }
    )
    public ResponseEntity<ProductResponse> retrieveProduct(@PathVariable(name = "id")
                                                           @Valid
                                                           @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Invalid code format.")
                                                           String id) throws ProductNotFoundException {
        log.info("GET - Searching for a specific product id {}.", id);
        ProductResponse returnProduct = this.productService.retrieveProduct(id);

        return ResponseEntity.ok(returnProduct);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of products",
            tags = {ControllerConstant.PRODUCT},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ProductResponse.class
                                            )))),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )))
            }
    )
    public ResponseEntity<List<ProductResponse>> retrieveListProducts() throws ProductNotFoundException {
        log.info("GET - Searching all products.");
        List<ProductResponse> listProducts = this.productService.retrieveListProducts();

        return ResponseEntity.ok(listProducts);
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update product information",
            tags = {ControllerConstant.PRODUCT},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ProductResponse.class
                                    ))),
                    @ApiResponse(description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )))
            }
    )
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody
                                                         @Valid
                                                         ProductRequest productRequest) throws ProductCreateException {
        log.info("PUT - Update product information {}.", productRequest);
        ProductResponse returnProduct = this.productService.createProduct(productRequest);

        return ResponseEntity.ok(returnProduct);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a product",
            tags = {ControllerConstant.PRODUCT},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ProductResponse.class
                                    ))),
                    @ApiResponse(description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )))
            }
    )
    public ResponseEntity<ProductResponse> createProduct(@RequestBody
                                                         @Valid
                                                         ProductRequest productRequest,
                                                         UriComponentsBuilder uriBuilder) throws ProductCreateException {
        log.info("POST - Create a product {}.", productRequest);
        ProductResponse returnProduct = this.productService.createProduct(productRequest);

        var uri = uriBuilder.path("/product/{id}").buildAndExpand(returnProduct.getCode()).toUri();

        return ResponseEntity.created(uri).body(returnProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a product",
            tags = {ControllerConstant.PRODUCT},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )))
            }
    )
    public ResponseEntity<String> deleteProduct(@PathVariable
                                                @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Invalid code format.")
                                                String id) throws ProductDeleteException {
        log.info("DELETE - Delete a product {}.", id);
        this.productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
