package com.example.project.validation.validators;

import com.example.project.validation.annotations.Password;

public class PasswordValidators extends AbstractRegexValidator<Password> {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[^\\s]{8,}$";

    @Override
    protected String getPattern() {
        return PASSWORD_PATTERN;
    }

    @Override
    protected void init(Password annotation) {
        this.allowEmpty = annotation.allowEmpty();
    }
}
