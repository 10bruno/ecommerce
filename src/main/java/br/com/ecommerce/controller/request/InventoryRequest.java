package br.com.ecommerce.controller.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryRequest {
    @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
    private Integer id;
    @DecimalMin(value = "0", inclusive = false, message = "Available quantity must be greater than 0.")
    @Digits(integer = 5, fraction = 0, message = "Available Quantity format is NNNNN.")
    private BigDecimal availableQuantity;
    @DecimalMin(value = "0", inclusive = false, message = "Reserved quantity must be greater than 0.")
    @Digits(integer = 5, fraction = 0, message = "Reserved Quantity format is NNNNN.")
    private BigDecimal reservedQuantity;
    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "{product.code.format}")
    private String productCode;
}
