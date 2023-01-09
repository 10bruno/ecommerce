package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.infra.exception.CustomerCreateException;
import br.com.ecommerce.infra.exception.CustomerDeleteException;
import br.com.ecommerce.infra.exception.CustomerNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import br.com.ecommerce.domain.service.CustomerService;
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
@RequestMapping("/customer")
@Tag(name = ControllerConstant.CUSTOMER, description = "CRUD of the customer service.")
@Slf4j
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{cpf}")
    @Operation(
            method = "GET",
            summary = "Retrieve a customer by cpf",
            tags = {ControllerConstant.CUSTOMER},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = CustomerResponse.class
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
    public ResponseEntity<CustomerResponse> retrieveCustomer(@PathVariable(name = "cpf")
                                                             @Valid
                                                             @Pattern(regexp = "^\\d{11}$", message = "Cpf must have 11 characters and only numbers.")
                                                             String cpf) throws CustomerNotFoundException {
        log.info("GET - Searching for a specific customer cpf {}.", cpf);
        CustomerResponse returnCustomer = this.customerService.retrieveCustomer(cpf);

        return ResponseEntity.ok(returnCustomer);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of customers",
            tags = {ControllerConstant.CUSTOMER},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CustomerResponse.class
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
    public ResponseEntity<List<CustomerResponse>> retrieveListCustomers() throws CustomerNotFoundException {
        log.info("GET - Searching all customers.");
        List<CustomerResponse> listCustomers = this.customerService.retrieveListCustomers();

        return ResponseEntity.ok(listCustomers);
    }

    @PutMapping
    @Operation(
            method = "PUT",
            summary = "Update customer information",
            tags = {ControllerConstant.CUSTOMER},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = CustomerResponse.class
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
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody
                                                           @Valid
                                                           CustomerRequest customer) throws CustomerCreateException {
        log.info("PUT - Update customer information {}.", customer);
        CustomerResponse returnCustomer = this.customerService.createCustomer(customer);

        return ResponseEntity.ok(returnCustomer);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Create a customer",
            tags = {ControllerConstant.CUSTOMER},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = CustomerResponse.class
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
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody
                                                           @Valid
                                                           CustomerRequest customer, UriComponentsBuilder uriBuilder) throws CustomerCreateException {
        log.info("POST - Create a customer {}.", customer);
        CustomerResponse returnCustomer = this.customerService.createCustomer(customer);

        var uri = uriBuilder.path("/customer/{cpf}").buildAndExpand(returnCustomer.getCpf()).toUri();

        return ResponseEntity.created(uri).body(returnCustomer);
    }

    @DeleteMapping("/{cpf}")
    @Operation(
            method = "DELETE",
            summary = "Delete a customer",
            tags = {ControllerConstant.CUSTOMER},
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
    public ResponseEntity<String> deleteCustomer(@PathVariable
                                                 @Pattern(regexp = "^\\d{11}$", message = "Cpf must have 11 characters and only numbers.")
                                                 String cpf) throws CustomerDeleteException {
        log.info("DELETE - Delete a customer cpf {}.", cpf);
        this.customerService.deleteCustomer(cpf);

        return ResponseEntity.noContent().build();
    }
}
