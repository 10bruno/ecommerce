package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.controller.response.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.response.exception.ProductCreateException;
import br.com.ecommerce.controller.response.exception.ProductDeleteException;
import br.com.ecommerce.controller.response.exception.ProductNotFoundException;
import br.com.ecommerce.controller.response.handler.ErrorResponse;
import br.com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "CRUD of the product service.")
@Slf4j
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
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public ProductResponse retrieveProduct(@PathVariable Integer id) throws ProductNotFoundException {
        log.info("GET - Searching for a specific product id {}.", id);
        return this.productService.retrieveProduct(id);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of products",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public List<ProductResponse> retrieveListProducts() throws CustomerNotFoundException {
        log.info("GET - Searching all products.");
        return this.productService.retrieveListProducts();
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update specific product information",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest) throws ProductCreateException {
        log.info("PUT - Update specific product information {}.", productRequest);
        return this.productService.createOrUpdateProduct(productRequest);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a product",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public ProductResponse createOrUpdateProduct(@RequestBody ProductRequest productRequest) throws ProductCreateException {
        log.info("POST - Create a product {}.", productRequest);
        return this.productService.createOrUpdateProduct(productRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a product",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public void deleteProduct(@PathVariable Integer id) throws ProductDeleteException {
        log.info("DELETE - Delete a product {}.", id);
        this.productService.deleteProduct(id);
    }
}
