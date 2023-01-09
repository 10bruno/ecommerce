package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.controller.common.constant.ControllerConstant;
import br.com.ecommerce.infra.exception.HistoricCreateException;
import br.com.ecommerce.infra.exception.HistoricDeleteException;
import br.com.ecommerce.infra.exception.HistoricNotFoundException;
import br.com.ecommerce.infra.handler.ErrorResponse;
import br.com.ecommerce.domain.service.HistoricService;
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
@RequestMapping("/historic")
@Tag(name = ControllerConstant.HISTORIC, description = "CRUD of the historic service.")
@Slf4j
@Validated
public class HistoricController {

    private final HistoricService historicService;

    @Autowired
    public HistoricController(HistoricService historicService) {
        this.historicService = historicService;
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Retrieve a historic by id",
            tags = {ControllerConstant.HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = HistoricResponse.class
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
    public ResponseEntity<HistoricResponse> retrieveHistoric(@PathVariable
                                                             @Valid
                                                             @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
                                                             Integer id) throws HistoricNotFoundException {
        log.info("GET - Searching for a specific historic id {}.", id);
        HistoricResponse returnHistoric = this.historicService.retrieveHistoric(id);

        return ResponseEntity.ok(returnHistoric);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of historics",
            tags = {ControllerConstant.HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = HistoricResponse.class
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
    public ResponseEntity<List<HistoricResponse>> retrieveListHistorics() throws HistoricNotFoundException {
        log.info("GET - Searching all historics.");
        List<HistoricResponse> listHistorics = this.historicService.retrieveListHistorics();

        return ResponseEntity.ok(listHistorics);
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update historic information",
            tags = {ControllerConstant.HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = HistoricResponse.class
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
    public ResponseEntity<HistoricResponse> updateHistoric(@RequestBody
                                                           @Valid
                                                           HistoricRequest historicRequest) throws HistoricCreateException {
        log.info("PUT - Update historic information {}", historicRequest);
        HistoricResponse returnHistoric = this.historicService.createHistoric(historicRequest);

        return ResponseEntity.ok(returnHistoric);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a historic",
            tags = {ControllerConstant.HISTORIC},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = ControllerConstant.HTTP_STATUS_CODE_OK,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = HistoricResponse.class
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
    public ResponseEntity<HistoricResponse> createHistoric(@RequestBody
                                                           @Valid
                                                           HistoricRequest historicRequest,
                                                           UriComponentsBuilder uriBuilder) throws HistoricCreateException {
        log.info("POST - Create a historic {}", historicRequest);
        HistoricResponse returnHistoric = this.historicService.createHistoric(historicRequest);

        var uri = uriBuilder.path("/historic/{id}").buildAndExpand(returnHistoric.getId()).toUri();

        return ResponseEntity.created(uri).body(returnHistoric);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a historic",
            tags = {ControllerConstant.HISTORIC},
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
                                                 @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
                                                 Integer id) throws HistoricDeleteException {
        log.info("DELETE - Delete a historic {}", id);
        this.historicService.deleteHistoric(id);

        return ResponseEntity.noContent().build();
    }
}
