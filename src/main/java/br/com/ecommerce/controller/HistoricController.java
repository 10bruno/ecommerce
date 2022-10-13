package br.com.ecommerce.controller;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.controller.response.exception.HistoricCreateException;
import br.com.ecommerce.controller.response.exception.HistoricDeleteException;
import br.com.ecommerce.controller.response.exception.HistoricNotFoundException;
import br.com.ecommerce.controller.response.handler.ErrorResponse;
import br.com.ecommerce.service.HistoricService;
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
@RequestMapping("/historic")
@Tag(name = "Historic", description = "CRUD of the historic service.")
@Slf4j
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
            tags = {"Historic"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public HistoricResponse retrieveHistoric(@PathVariable Integer id) throws HistoricNotFoundException {
        log.info("GET - Searching for a specific historic id {}.", id);
        return this.historicService.retrieveHistoric(id);
    }

    @GetMapping()
    @Operation(
            method = "GET",
            summary = "Retrieve a list of historics",
            tags = {"Historic"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException {
        log.info("GET - Searching all historics.");
        return this.historicService.retrieveListHistorics();
    }

    @PutMapping()
    @Operation(
            method = "PUT",
            summary = "Update specific historic information",
            tags = {"Historic"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public HistoricResponse updateHistoric(@RequestBody HistoricRequest historicRequest) throws HistoricCreateException {
        log.info("PUT - Update specific historic information {}", historicRequest);
        return this.historicService.createOrUpdateHistoric(historicRequest);
    }

    @PostMapping()
    @Operation(
            method = "POST",
            summary = "Create a historic",
            tags = {"Historic"},
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
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
    public HistoricResponse createOrUpdateHistoric(@RequestBody HistoricRequest historicRequest) throws HistoricCreateException {
        log.info("POST - Create a historic {}", historicRequest);
        return this.historicService.createOrUpdateHistoric(historicRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a historic",
            tags = {"Historic"},
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
    public void deleteHistoric(@PathVariable Integer id) throws HistoricDeleteException {
        log.info("DELETE - Delete a historic {}", id);
        this.historicService.deleteHistoric(id);
    }
}
