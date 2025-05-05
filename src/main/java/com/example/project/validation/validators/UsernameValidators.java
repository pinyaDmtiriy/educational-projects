package com.example.project.validation.validators;

import com.example.project.validation.annotations.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidators implements ConstraintValidator<Username, String> {

    private boolean allowEmpty;
    private static final String PASSWORD_PATTERN ="^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{3,20}(?<![_.])$";

    @Override
    public void initialize(Username constraintAnnotation) {
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isBlank())
            return allowEmpty;

        return value.matches(PASSWORD_PATTERN);
    }
}
