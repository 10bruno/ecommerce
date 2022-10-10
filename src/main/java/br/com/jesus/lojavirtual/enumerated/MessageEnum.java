package br.com.jesus.lojavirtual.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {

    CUSTOMER_NOT_FOUND_EXCEPTION("Customer not found for this cpf."),
    CUSTOMERS_LIST_NOT_FOUND_EXCEPTION("No customer found."),
    CUSTOMER_ERROR_ON_CREATE("Customer error on create.");

    private final String value;

}
