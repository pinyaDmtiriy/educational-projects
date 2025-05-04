package com.example.project.validation.annotations;

import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
@NotBlank(message = "Username cannot be blank")
@Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{3,20}(?<![_.])$", message = "Username can only contain alphanumeric characters, dots, and underscores, but not start or end with them, and no consecutive dots or underscores.")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "invalid username";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
