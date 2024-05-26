package br.com.ecommerce.controller;

import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.domain.service.PaymentHistoricService;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/historic")
@Tag(name = ControllerConstant.PAYMENT_HISTORIC, description = "CRUD of the historic service.")
@Slf4j
@Validated
public class PaymentHistoricController {

    private final PaymentHistoricService paymentHistoricService;

    @Autowired
    public PaymentHistoricController(PaymentHistoricService paymentHistoricService) {
        this.paymentHistoricService = paymentHistoricService;
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Retrieve a historic by id",
            tags = {ControllerConstant.PAYMENT_HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentHistoricResponse.class
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
    public ResponseEntity<PaymentHistoricResponse> retrieveHistoric(@PathVariable
                                                             @Valid
                                                             @Positive
                                                             Integer id) throws PaymentHistoricNotFoundException {
        log.info("GET - Searching for a specific historic id {}.", id);
        PaymentHistoricResponse returnHistoric = this.paymentHistoricService.retrieveHistoric(id);

        return ResponseEntity.ok(returnHistoric);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of historics",
            tags = {ControllerConstant.PAYMENT_HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = PaymentHistoricResponse.class
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
    public ResponseEntity<List<PaymentHistoricResponse>> retrieveListHistorics() throws PaymentHistoricNotFoundException {
        log.info("GET - Searching all historics.");
        List<PaymentHistoricResponse> listHistorics = this.paymentHistoricService.retrieveListHistorics();

        return ResponseEntity.ok(listHistorics);
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update historic information",
            tags = {ControllerConstant.PAYMENT_HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentHistoricResponse.class
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
    public ResponseEntity<PaymentHistoricResponse> updateHistoric(@RequestBody
                                                           @Valid
                                                                  PaymentHistoricRequest paymentHistoricRequest) throws PaymentHistoricCreateException {
        log.info("PUT - Update historic information {}", paymentHistoricRequest);
        PaymentHistoricResponse returnHistoric = this.paymentHistoricService.createHistoric(paymentHistoricRequest);

        return ResponseEntity.ok(returnHistoric);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a historic",
            tags = {ControllerConstant.PAYMENT_HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentHistoricResponse.class
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
    public ResponseEntity<PaymentHistoricResponse> createHistoric(@RequestBody
                                                           @Valid
                                                                      PaymentHistoricRequest paymentHistoricRequest,
                                                                  UriComponentsBuilder uriBuilder) throws PaymentHistoricCreateException {
        log.info("POST - Create a historic {}", paymentHistoricRequest);
        PaymentHistoricResponse returnHistoric = this.paymentHistoricService.createHistoric(paymentHistoricRequest);

        var uri = uriBuilder.path("/historic/{id}").buildAndExpand(returnHistoric.getId()).toUri();

        return ResponseEntity.created(uri).body(returnHistoric);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a historic",
            tags = {ControllerConstant.PAYMENT_HISTORIC},
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
    public ResponseEntity<String> deleteHistoric(@PathVariable
                                                 @Positive
                                                 Integer id) throws PaymentHistoricDeleteException {
        log.info("DELETE - Delete a historic {}", id);
        this.paymentHistoricService.deleteHistoric(id);

        return ResponseEntity.noContent().build();
    }
}
