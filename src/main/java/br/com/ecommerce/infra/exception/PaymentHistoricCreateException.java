package br.com.ecommerce.infra.exception;

import java.io.Serial;
import java.io.Serializable;

public class PaymentHistoricCreateException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 103L;

    public PaymentHistoricCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
