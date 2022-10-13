package br.com.ecommerce.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "parameter.example")
public class ParameterConfig {
    private String[] sequence;
    private List<String> list;
}
