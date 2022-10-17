package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class ProductDeleteException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 110L;
    public ProductDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
