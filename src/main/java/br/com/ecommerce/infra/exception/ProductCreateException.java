package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class ProductCreateException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 109L;
    public ProductCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
