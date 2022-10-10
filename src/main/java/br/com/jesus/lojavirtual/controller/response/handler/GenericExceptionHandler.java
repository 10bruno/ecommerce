package br.com.jesus.lojavirtual.controller.response.handler;

import br.com.jesus.lojavirtual.controller.response.exception.CustomerCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerNotFoundException;
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
            {CustomerNotFoundException.class})
    public ResponseEntity<Object> handleExceptionNotFound(Exception exception) {
        return getErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            {CustomerCreateException.class})
    public ResponseEntity<Object> handleExceptionInternalError(Exception exception) {
        return getErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
