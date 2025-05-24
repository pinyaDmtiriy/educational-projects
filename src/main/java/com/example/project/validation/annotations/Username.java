package com.example.project.validation.annotations;

import com.example.project.validation.validators.UsernameValidators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidators.class)
public @interface Username {
    String message() default "invalid username";
    boolean allowEmpty() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
