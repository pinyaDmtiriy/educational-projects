package com.example.project.exception;

import com.example.project.pojo.ErrorResponse;
import com.example.project.exception.ex.BANNED;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BANNED.class)
    public ResponseEntity<ErrorResponse> banned() {
        log.info("status = banned");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.fromBanned());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.fromBindingResult(ex.getBindingResult()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataAccessException(DataAccessException e) {
        if(e instanceof BadSqlGrammarException) {
            log.error("Неверный sql запрос");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.fromBadSql());
        } else if (e instanceof DuplicateKeyException) {
            log.error("Запись уже существует");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.message("the entry already exists"));
        }else if (e instanceof EmptyResultDataAccessException) {
            log.error("запись не найдена");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.message("not found"));
        }else if (e instanceof CannotGetJdbcConnectionException) {
            log.error("проблема с подключением к базе данных");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.message("server error"));
        } else if (e instanceof QueryTimeoutException) {
            log.error("время запроса истекло");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.message("request timed out"));
        }else if(e instanceof DataIntegrityViolationException) {
            log.error("нарушение настроек базы данных");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.message("incorrect data"));
        }else {
            log.error("Error DataAccessException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.message("server problems"));
        }
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.message("Token expired"));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.message("Invalid signature"));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.message("Malformed token"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity.BodyBuilder handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
