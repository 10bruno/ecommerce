package br.com.ecommerce.controller.common.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {

    CUSTOMER_NOT_FOUND_EXCEPTION("Customer not found for this cpf."),
    CUSTOMERS_LIST_NOT_FOUND_EXCEPTION("No customer found."),
    CUSTOMER_ERROR_ON_CREATE_EXCEPTION("Customer error on create."),
    CUSTOMER_ERROR_ON_DELETE_EXCEPTION("Customer error on delete."),
    PAYMENT_HISTORIC_NOT_FOUND_EXCEPTION("Payment Historic not found for this id."),
    PAYMENT_HISTORIC_LIST_NOT_FOUND_EXCEPTION("No payment historics found."),
    PAYMENT_HISTORIC_ERROR_ON_CREATE_EXCEPTION("Payment Historic error on create."),
    PAYMENT_HISTORIC_ERROR_ON_DELETE_EXCEPTION("Payment Historic error on delete."),
    PRODUCT_NOT_FOUND_EXCEPTION("Product not found for this id."),
    PRODUCT_LIST_NOT_FOUND_EXCEPTION("No products found."),
    PRODUCT_ERROR_ON_CREATE_EXCEPTION("Product error on create."),
    PRODUCT_ERROR_ON_DELETE_EXCEPTION("Product error on delete."),
    INVENTORY_NOT_FOUND_EXCEPTION("Inventory not found for this id."),
    INVENTORY_LIST_NOT_FOUND_EXCEPTION("No inventories found."),
    INVENTORY_ERROR_ON_CREATE_EXCEPTION("Inventory error on create."),
    INVENTORY_ERROR_ON_DELETE_EXCEPTION("Inventory error on delete.");

    private final String value;

}
