package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.controller.response.exception.CustomerCreateException;
import br.com.ecommerce.controller.response.exception.CustomerDeleteException;
import br.com.ecommerce.controller.response.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.response.handler.ErrorResponse;
import br.com.ecommerce.service.CustomerService;
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
@RequestMapping("/customer")
@Tag(name = "Customer", description = "CRUD of the customer service.")
@Slf4j
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
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public CustomerResponse retrieveCustomer(@PathVariable String cpf) throws CustomerNotFoundException {
        log.info("GET - Searching for a specific customer cpf {}.", cpf);
        return this.customerService.retrieveCustomer(cpf);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of customers",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public List<CustomerResponse> retrieveListCustomers() throws CustomerNotFoundException {
        log.info("GET - Searching all customers.");
        return this.customerService.retrieveListCustomers();
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update specific customer information",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customer) throws CustomerCreateException {
        log.info("PUT - Update specific customer information {}.", customer);
        return this.customerService.createOrUpdateCustomer(customer);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a customer",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public CustomerResponse createOrUpdateCustomer(@RequestBody CustomerRequest customer) throws CustomerCreateException {
        log.info("POST - Create a customer {}.", customer);
        return this.customerService.createOrUpdateCustomer(customer);
    }

    @DeleteMapping("/{cpf}")
    @Operation(
            method = "DELETE",
            summary = "Delete a customer",
            tags = {"Customer"},
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
    public void deleteCustomer(@PathVariable String cpf) throws CustomerDeleteException {
        log.info("DELETE - Delete a customer cpf {}.", cpf);
        this.customerService.deleteCustomer(cpf);
    }
}
