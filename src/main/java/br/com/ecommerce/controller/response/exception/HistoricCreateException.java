package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class HistoricCreateException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 103L;

    public HistoricCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
