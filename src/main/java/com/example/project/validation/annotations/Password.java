package com.example.project.validation.annotations;

import com.example.project.validation.validators.PasswordValidators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidators.class)
public @interface Password {
    String message() default "invalid password";
    boolean allowEmpty() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
