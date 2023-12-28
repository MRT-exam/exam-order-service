package com.mtgo.exam.orderservice.exception;

import com.mtgo.exam.orderservice.exception.error.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorObject> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error(ex.getLocalizedMessage(), ex);


        return buildErrorResponse(
                Objects.nonNull(ex.getLocalizedMessage()) ? ex.getLocalizedMessage() : ErrorMessages.ORDER_NOT_FOUND_ERROR,
                HttpStatus.NOT_FOUND,
                new Date());
    }

    // Add More Exceptions here



    private ResponseEntity<ErrorObject> buildErrorResponse(String message, HttpStatus status, Date date) {
        return ResponseEntity.status(status).body(new ErrorObject(message, status.value(), date));
    }
}
