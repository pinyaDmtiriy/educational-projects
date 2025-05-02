package com.example.project.exception;

import com.example.project.exception.ex.BANNED;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BANNED.class)
    public ResponseEntity<ResponseDto> banned(BANNED ex) {
        return ResponseEntity.ok(new ResponseDto(ex.getMessage(),HttpStatus.FORBIDDEN));
    }

    private record ResponseDto(String message, HttpStatus httpStatus) {}

}
