package br.com.jesus.lojavirtual.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private String cpf;
    private String name;
    private String birthDate;
    private String gender;
}
