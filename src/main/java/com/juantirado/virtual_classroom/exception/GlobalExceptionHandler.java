package com.juantirado.virtual_classroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerValidatorsErrors(MethodArgumentNotValidException ex) {
        var response = new HashMap<String, Object>();
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        var errores = new HashMap<>();
        for (var fieldError : fieldErrors) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errores);
        response.put("message", "Validacion fallida");
        return ResponseEntity.badRequest().body(response);
    }
}