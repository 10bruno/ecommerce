package br.com.ecommerce.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaymentHistoricRequest {
    @Positive(message = "Id must be positive")
    private Integer id;
    @NotBlank(message = "Description is required.")
    private String description;
    private String type;
    private LocalDate date;
}
