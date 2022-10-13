package br.com.ecommerce.parameter;

import br.com.ecommerce.config.ParameterConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ParameterService {

    @Value("${parameter.example.sequence}")
    private String[] sequence;

    @Value("${parameter.example.list}")
    private List<String> list;

    private final ParameterConfig parameterConfig;

    @Autowired
    public ParameterService(ParameterConfig parameterConfig) {
        this.parameterConfig = parameterConfig;
    }

    public void getSequenceParameter() {
        List<String> paramSequence = Arrays.stream(parameterConfig.getSequence()).toList();
        log.info("GET - Searching sequence of parameter.");
        log.info("<----------------------------------------------------------------");
        log.info("Parameter of TestConfig {} ", paramSequence);
        log.info("Parameter of @Value {} ", (Object) sequence);
        log.info("---------------------------------------------------------------->");
    }

    public void getListParameter() {
        List<String> paramList = parameterConfig.getList();
        log.info("GET - Searching list of parameters.");
        log.info("<----------------------------------------------------------------");
        log.info("Parameter of TestConfig {} ", paramList);
        log.info("Parameter of @Value {} ", list);
        log.info("---------------------------------------------------------------->");
    }

}
