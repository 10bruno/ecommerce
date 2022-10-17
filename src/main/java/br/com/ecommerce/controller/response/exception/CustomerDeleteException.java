package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class CustomerDeleteException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 101L;

    public CustomerDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
