package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class CustomerCreateException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 100L;

    public CustomerCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
