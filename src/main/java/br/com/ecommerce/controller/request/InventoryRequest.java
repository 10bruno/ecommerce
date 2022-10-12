package br.com.ecommerce.controller.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryRequest {
    private Integer id;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private String productCode;
}
