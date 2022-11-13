package com.system.banking.handlers;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Collections.singletonList;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNumberNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleException(AccountNumberNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Try using correct Account Number", singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
