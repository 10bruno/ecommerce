package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class PaymentHistoricNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 105L;
    public PaymentHistoricNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}