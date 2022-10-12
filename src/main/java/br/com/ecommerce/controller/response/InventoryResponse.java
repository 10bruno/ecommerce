package br.com.ecommerce.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryResponse {
    private Integer id;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private String productCode;
}
