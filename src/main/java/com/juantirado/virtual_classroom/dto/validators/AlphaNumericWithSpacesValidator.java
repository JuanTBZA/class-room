package com.juantirado.virtual_classroom.dto.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaNumericWithSpacesValidator
        implements ConstraintValidator<AlphaNumericWithSpaces, String> {

    private static final String REGEX = "^[a-zA-Z0-9 ]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return value.matches(REGEX);
    }
}