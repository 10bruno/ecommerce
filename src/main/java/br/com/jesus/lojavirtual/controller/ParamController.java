package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.param.ParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lojavirtual/param")
@Slf4j
public class ParamController {

    private final ParameterService parameterService;

    @Autowired
    public ParamController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @GetMapping("/list")
    public void getList() {
        this.parameterService.getListParameter();
    }

    @GetMapping("/sequence")
    public void getParameters() {
        this.parameterService.getSequenceParameter();
    }

}
