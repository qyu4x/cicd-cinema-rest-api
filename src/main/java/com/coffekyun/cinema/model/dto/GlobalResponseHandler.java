package com.coffekyun.cinema.model.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", message);
        response.put("code", httpStatus.value());
        response.put("data", data);

        return new ResponseEntity<>(response, httpStatus);
    }

}
