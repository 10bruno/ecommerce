package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class ProductNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 111L;
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
