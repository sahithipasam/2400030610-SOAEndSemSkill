package com.tourist.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        HttpStatus status = "Invalid token".equals(exception.getMessage())
            ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid request"));
    }
}
