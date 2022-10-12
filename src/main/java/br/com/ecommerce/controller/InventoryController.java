package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.controller.response.exception.InventoryCreateException;
import br.com.ecommerce.controller.response.exception.InventoryNotFoundException;
import br.com.ecommerce.controller.response.handler.ErrorResponse;
import br.com.ecommerce.service.InventoryService;
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
@RequestMapping("/lojavirtual/inventory")
@Tag(name = "Inventory", description = "CRUD of the inventory service.")
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Retrieve a inventory by id",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = InventoryResponse.class
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
    public InventoryResponse retrieveInventory(@PathVariable String id) throws InventoryNotFoundException {
        log.info("GET - Searching for a specific inventory id {}.", id);
        return this.inventoryService.retrieveInventory(id);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of inventories",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = InventoryResponse.class
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
    public List<InventoryResponse> retrieveListInventories() throws InventoryNotFoundException {
        log.info("GET - Searching all inventories.");
        return this.inventoryService.retrieveListInventories();
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update specific inventory information",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = InventoryResponse.class
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
    public InventoryResponse updateInventory(@RequestBody InventoryRequest inventoryRequest) throws InventoryCreateException {
        log.info("PUT - Update specific inventory information {}.", inventoryRequest);
        return this.inventoryService.createOrUpdateInventory(inventoryRequest);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a inventory",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = InventoryResponse.class
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
    public InventoryResponse createOrUpdateInventory(@RequestBody InventoryRequest inventoryRequest) throws InventoryCreateException {
        log.info("POST - Create a inventory {}.", inventoryRequest);
        return this.inventoryService.createOrUpdateInventory(inventoryRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a inventory",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = InventoryResponse.class
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
    public void deleteInventory(@PathVariable String id) {
        log.info("DELETE - Delete a inventory {}.", id);
        this.inventoryService.deleteInventory(id);
    }
}