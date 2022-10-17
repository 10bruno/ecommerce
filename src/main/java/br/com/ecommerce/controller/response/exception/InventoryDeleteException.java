package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class InventoryDeleteException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 107L;
    public InventoryDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
