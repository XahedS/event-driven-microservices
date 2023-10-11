package com.microservices.demo.elastic.query.service.common.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //Enables exception handling globally (or across the whole application)
public class ElasticQueryServiceErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticQueryServiceErrorHandler.class);
    @ExceptionHandler(AccessDeniedException.class)
    //The annotation sets a specific exception to handle; e.g. at the moment will only handle AccessDeniedExption as
    //passed in the arguments.
    public ResponseEntity<String> handle(AccessDeniedException e){
        LOG.error("Access denied exception!", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to access this resource");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    //The annotation sets a specific exception to handle; e.g. at the moment will only handle IllegalArgumentException as
    //passed in the arguments.
    public ResponseEntity<String> handle(IllegalArgumentException e){
        LOG.error("Illegal argument exception!", e);
        return ResponseEntity.badRequest().body("Illegal argument exception! " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    //The annotation sets a specific exception to handle; e.g. at the moment will only handle RuntimeException as
    //passed in the arguments.
    public ResponseEntity<String> handle(RuntimeException e){
        LOG.error("Service runtime exception!", e);
        return ResponseEntity.badRequest().body("Service runtime exception!" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    //The annotation sets a specific exception to handle; e.g. at the moment will only handle Exception as
    //passed in the arguments.
    public ResponseEntity<String> handle(Exception e){
        LOG.error("Internal Server Error ! ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occurred!" + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //The annotation sets a specific exception to handle; e.g. at the moment will only handle MethodArgumentNotValidException
    // as passed in the arguments.
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException e){
        LOG.error("Method argument validation exception", e);
        Map<String, String> errors  = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error ->
                errors.put(((FieldError)error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
