package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class InventoryCreateException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 106L;
    public InventoryCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
