package com.example.project.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public abstract class AbstractRegexValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    protected boolean allowEmpty;

    protected abstract String getPattern();

    protected abstract void init(A annotation);

    @Override
    public void initialize(A annotation) {
        init(annotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank())
            return allowEmpty;

        return value.matches(getPattern());
    }
}
