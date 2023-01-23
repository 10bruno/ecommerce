package br.com.ecommerce.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {

    @Pattern(regexp = "^\\d{11}$", message = "Cpf must have 11 characters and only numbers.")
    private String cpf;
    @NotBlank(message = "Name is required.")
    private String name;
    private String birthDate;
    @Pattern(regexp = "^$|[FM]$", message = "Gender must be F or M.")
    private String gender;
}
