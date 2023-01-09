package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class InventoryNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 108L;
    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
