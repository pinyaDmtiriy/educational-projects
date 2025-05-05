package com.example.project.validation.validators;

import com.example.project.validation.annotations.Username;

public class UsernameValidators extends AbstractRegexValidator<Username> {

    private static final String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{1,20}(?<![_.])$";

    @Override
    protected String getPattern() {
        return USERNAME_PATTERN;
    }

    @Override
    protected void init(Username annotation) {
        this.allowEmpty = annotation.allowEmpty();
    }
}
