package br.com.ecommerce.controller;

import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.infra.exception.ProductCreateException;
import br.com.ecommerce.infra.exception.ProductDeleteException;
import br.com.ecommerce.infra.exception.ProductNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface ProductController {

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
    ResponseEntity<ProductResponse> retrieveProduct(@PathVariable(name = "id")
                                                    @Valid
                                                    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Invalid code format.")
                                                    String id) throws ProductNotFoundException;

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
    ResponseEntity<List<ProductResponse>> retrieveListProducts() throws ProductNotFoundException;

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
    ResponseEntity<ProductResponse> updateProduct(@RequestBody
                                                  @Valid
                                                  ProductRequest productRequest) throws ProductCreateException;

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
    ResponseEntity<ProductResponse> createProduct(@RequestBody
                                                  @Valid
                                                  ProductRequest productRequest,
                                                  UriComponentsBuilder uriBuilder) throws ProductCreateException;

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
    ResponseEntity<String> deleteProduct(@PathVariable
                                         @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Invalid code format.")
                                         String id) throws ProductDeleteException;
}
