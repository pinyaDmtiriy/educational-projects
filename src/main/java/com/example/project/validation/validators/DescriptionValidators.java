package com.example.project.validation.validators;

import com.example.project.validation.annotations.Description;

public class DescriptionValidators extends AbstractRegexValidator<Description> {
    private static final String DESCRIPTION_PATTERN = "^$|^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{10,300}$";

    @Override
    protected String getPattern() {
        return DESCRIPTION_PATTERN;
    }

    @Override
    protected void init(Description annotation) {
        this.allowEmpty = annotation.allowEmpty();
    }

}
