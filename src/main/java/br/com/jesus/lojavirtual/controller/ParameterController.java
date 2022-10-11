package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.param.ParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lojavirtual/parameter")
@Tag(name = "Parameter", description = "Example of use parameters on application.yaml.")
@Slf4j
public class ParameterController {

    private final ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @GetMapping("/list")
    @Operation(
            method = "GET",
            summary = "Retrieve a list of parameters.",
            tags = {"Parameter"})
    public void retrieveListParameters() {
        this.parameterService.getListParameter();
    }

    @GetMapping("/sequence")
    @Operation(
            method = "GET",
            summary = "Retrieve a sequence of parameters.",
            tags = {"Parameter"})
    public void getSequenceParameters() {
        this.parameterService.getSequenceParameter();
    }

}