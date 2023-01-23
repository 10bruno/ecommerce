package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class PaymentHistoricDeleteException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 104L;
    public PaymentHistoricDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
