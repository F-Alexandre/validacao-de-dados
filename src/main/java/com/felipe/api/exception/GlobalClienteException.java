package com.felipe.api.exception;

import org.springframework.validation.FieldError;
import org.springframework.http.*;
import java.time.LocalDateTime;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalClienteException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String , String> erros = new HashMap<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            erros.put(error.getField(), error.getDefaultMessage());
        }

        Map<String , Object> resposta = new HashMap<>();
        resposta.put("timestamp" , LocalDateTime.now());
        resposta.put("status" , HttpStatus.BAD_REQUEST.value());
        resposta.put("erros" , erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
}
