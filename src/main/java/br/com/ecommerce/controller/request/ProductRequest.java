package br.com.ecommerce.controller.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductRequest {

    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "{product.code.format}")
    private String code;
    @NotBlank(message = "{product.category.required}")
    private String category;
    private String title;
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "{product.weight.minimum}")
    @Digits(integer = 5, fraction = 2, message = "{product.weight.format}")
    private BigDecimal weight;
    private LocalDate dateRegister;
}
