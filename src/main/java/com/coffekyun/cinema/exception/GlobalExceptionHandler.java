package com.coffekyun.cinema.exception;

import org.apache.http.protocol.HTTP;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, List<String>> responseBody = new HashMap<>();

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map<String, ?>> dataNotFoundHandler(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", message);
        response.put("code", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Map<String, ?>> dataAlreadyExistsHandler(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", message);
        response.put("code", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public static ResponseEntity<Map<String, ?>> failedToGeneratePdf(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", message);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
