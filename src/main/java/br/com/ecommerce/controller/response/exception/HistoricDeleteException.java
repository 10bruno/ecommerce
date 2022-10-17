package br.com.ecommerce.controller.response.exception;

import java.io.Serial;
import java.io.Serializable;

public class HistoricDeleteException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 104L;
    public HistoricDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
