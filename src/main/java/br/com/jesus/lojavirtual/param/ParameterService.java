package br.com.jesus.lojavirtual.param;

import br.com.jesus.lojavirtual.config.ParamConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ParameterService {

    @Value("${param.example.sequence}")
    private String[] sequence;

    @Value("${param.example.list}")
    private List<String> list;

    private final ParamConfig paramConfig;

    @Autowired
    public ParameterService(ParamConfig paramConfig) {
        this.paramConfig = paramConfig;
    }

    public void getSequenceParameter() {
        List<String> paramSequence = Arrays.stream(paramConfig.getSequence()).toList();
        log.info("GET - Pesquisando sequencia parametros");
        log.info("<----------------------------------------------------------------");
        log.info("Parameter of TestConfig {} ", paramSequence);
        log.info("Parameter of @Value {} ", (Object) sequence);
        log.info("---------------------------------------------------------------->");
    }

    public void getListParameter() {
        List<String> paramList = paramConfig.getList();
        log.info("GET - Pesquisando lista parametros");
        log.info("<----------------------------------------------------------------");
        log.info("Parameter of TestConfig {} ", paramList);
        log.info("Parameter of @Value {} ", list);
        log.info("---------------------------------------------------------------->");
    }

}
