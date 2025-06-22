package com.juantirado.virtual_classroom.dto.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = AlphaNumericWithSpacesValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphaNumericWithSpaces {

    String message() default "Solo se permiten letras, números y espacios";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}