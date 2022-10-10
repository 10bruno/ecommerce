package br.com.jesus.lojavirtual.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {
    private String cpf;
    private String name;
    private String birthDate;
    private String gender;
}
