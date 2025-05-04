package com.example.project.exception;

import com.example.project.dto.ErrorResponse;
import com.example.project.exception.ex.BANNED;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BANNED.class)
    public ResponseEntity<ErrorResponse> banned() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.fromBanned());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.fromBindingResult(ex.getBindingResult()));
    }

}
