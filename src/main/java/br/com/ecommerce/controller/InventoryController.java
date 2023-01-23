package br.com.ecommerce.controller;

import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.domain.service.InventoryService;
import br.com.ecommerce.infra.exception.InventoryCreateException;
import br.com.ecommerce.infra.exception.InventoryDeleteException;
import br.com.ecommerce.infra.exception.InventoryNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Tag(name = ControllerConstant.INVENTORY, description = "CRUD of the inventory service.")
@Slf4j
@Validated
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
    public ResponseEntity<InventoryResponse> retrieveInventory(@PathVariable
                                                               @Valid
                                                               @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
                                                               Integer id) throws InventoryNotFoundException {
        log.info("GET - Searching for a specific inventory id {}.", id);
        InventoryResponse returnInventory = this.inventoryService.retrieveInventory(id);

        return ResponseEntity.ok(returnInventory);
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
    public ResponseEntity<List<InventoryResponse>> retrieveListInventories() throws InventoryNotFoundException {
        log.info("GET - Searching all inventories.");
        List<InventoryResponse> listInventories = this.inventoryService.retrieveListInventories();

        return ResponseEntity.ok(listInventories);
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update inventory information",
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
    public ResponseEntity<InventoryResponse> updateInventory(@RequestBody
                                                             @Valid
                                                             InventoryRequest inventoryRequest) throws InventoryCreateException {
        log.info("PUT - Update inventory information {}.", inventoryRequest);
        InventoryResponse returnInventory = this.inventoryService.createInventory(inventoryRequest);

        return ResponseEntity.ok(returnInventory);
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
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody
                                                             @Valid
                                                             InventoryRequest inventoryRequest,
                                                             UriComponentsBuilder uriBuilder) throws InventoryCreateException {
        log.info("POST - Create a inventory {}.", inventoryRequest);
        InventoryResponse returnInventory = this.inventoryService.createInventory(inventoryRequest);

        var uri = uriBuilder.path("/inventory/{id}").buildAndExpand(returnInventory.getId()).toUri();

        return ResponseEntity.created(uri).body(returnInventory);
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
    public ResponseEntity<String> deleteInventory(@PathVariable
                                                  @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
                                                  Integer id) throws InventoryDeleteException {
        log.info("DELETE - Delete a inventory {}.", id);
        this.inventoryService.deleteInventory(id);

        return ResponseEntity.noContent().build();
    }
}
