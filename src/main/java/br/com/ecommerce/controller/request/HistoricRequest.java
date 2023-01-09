package br.com.ecommerce.controller.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class HistoricRequest {
    @Pattern(regexp = "^\\d*$", message = "Id must have only numbers.")
    private Integer id;
    @NotBlank(message = "Description is required.")
    private String description;
    private String type;
    private LocalDate date;
}
