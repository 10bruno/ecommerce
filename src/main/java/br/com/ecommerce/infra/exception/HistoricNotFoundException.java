package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class HistoricNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 105L;
    public HistoricNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}