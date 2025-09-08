package br.com.ecommerce.infra.handler;

import br.com.ecommerce.infra.exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    private ResponseEntity<Object> getErrorResponse(String msg, Throwable cause, HttpStatus httpStatus) {
        log.error(msg);
        log.debug(cause.toString());
        ErrorResponse errorResponse = new ErrorResponse(msg);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(
            {CustomerNotFoundException.class,
                    PaymentHistoricNotFoundException.class,
                    InventoryNotFoundException.class,
                    ProductNotFoundException.class})
    public ResponseEntity<Object> handleExceptionNotFound(Exception exception, Throwable cause) {
        return getErrorResponse(exception.getMessage(), cause, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            {CustomerCreateException.class,
                    PaymentHistoricCreateException.class,
                    InventoryCreateException.class,
                    ProductCreateException.class,
                    CustomerDeleteException.class,
                    PaymentHistoricDeleteException.class,
                    InventoryDeleteException.class,
                    ProductDeleteException.class})
    public ResponseEntity<Object> handleExceptionInternalError(Exception exception, Throwable cause) {
        return getErrorResponse(exception.getMessage(), cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //entrace arguments, get/cpf
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintException(Exception exception, Throwable cause) {
        return getErrorResponse(exception.getMessage(), cause, HttpStatus.BAD_REQUEST);
    }

    //body parameters, post, put
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
    }

    private record ValidationErrorData(String field, String message) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
