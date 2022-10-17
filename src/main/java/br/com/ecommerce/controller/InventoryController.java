package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.controller.response.constant.ControllerConstant;
import br.com.ecommerce.controller.response.exception.InventoryCreateException;
import br.com.ecommerce.controller.response.exception.InventoryDeleteException;
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
@RequestMapping("/inventory")
@Tag(name = ControllerConstant.INVENTORY, description = "CRUD of the inventory service.")
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
            tags = {ControllerConstant.INVENTORY},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
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
    public InventoryResponse retrieveInventory(@PathVariable Integer id) throws InventoryNotFoundException {
        log.info("GET - Searching for a specific inventory id {}.", id);
        return this.inventoryService.retrieveInventory(id);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of inventories",
            tags = {ControllerConstant.INVENTORY},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
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
            tags = {ControllerConstant.INVENTORY},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
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
            tags = {ControllerConstant.INVENTORY},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
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
            tags = {ControllerConstant.INVENTORY},
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
    public void deleteInventory(@PathVariable Integer id) throws InventoryDeleteException {
        log.info("DELETE - Delete a inventory {}.", id);
        this.inventoryService.deleteInventory(id);
    }
}
