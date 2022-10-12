package br.com.ecommerce.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductResponse {
    private String code;
    private String category;
    private String title;
    private String description;
    private BigDecimal weight;
    private LocalDate dateRegister;
}
