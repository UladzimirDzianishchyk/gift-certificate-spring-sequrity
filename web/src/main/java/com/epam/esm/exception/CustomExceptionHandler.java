package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Error code", 1111);
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityIsExistException.class)
    public ResponseEntity<Object> handleEntityIsExistException(
            EntityIsExistException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Error code", 2222);
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Error code", 3333);
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<Object> handleJwtAuthenticationException(
            BadRequestException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Error code", 4444);
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
