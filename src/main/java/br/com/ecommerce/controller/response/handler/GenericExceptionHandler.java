package br.com.ecommerce.controller.response.handler;

import br.com.ecommerce.controller.response.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> getErrorResponse(String msg, HttpStatus httpStatus) {
        log.error(msg);
        ErrorResponse errorResponse = new ErrorResponse(msg);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(
            {CustomerNotFoundException.class,
                    HistoricNotFoundException.class,
                    InventoryNotFoundException.class,
                    ProductNotFoundException.class})
    public ResponseEntity<Object> handleExceptionNotFound(Exception exception) {
        return getErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            {CustomerCreateException.class,
                    HistoricCreateException.class,
                    InventoryCreateException.class,
                    ProductCreateException.class})
    public ResponseEntity<Object> handleExceptionInternalError(Exception exception) {
        return getErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
