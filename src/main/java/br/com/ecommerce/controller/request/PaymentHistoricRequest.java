package br.com.ecommerce.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaymentHistoricRequest {
    @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
    private Integer id;
    @NotBlank(message = "Description is required.")
    private String description;
    private String type;
    private LocalDate date;
}
