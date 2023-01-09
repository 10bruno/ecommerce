package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class CustomerNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 102L;

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
