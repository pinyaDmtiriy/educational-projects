package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String message;
    List<String> errors;

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
}
