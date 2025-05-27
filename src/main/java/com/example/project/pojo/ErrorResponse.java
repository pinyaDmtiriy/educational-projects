package com.example.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse fromBindingResult(BindingResult result) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return new ErrorResponse("Validation failed", errors);
    }

    public static ErrorResponse fromBanned() {
        return new ErrorResponse("access to account is restricted");
    }

    public static ErrorResponse fromBadSql() {
        return new ErrorResponse("server problems");
    }

    public static ErrorResponse message(String message) {
        return new ErrorResponse(message);
    }
}
