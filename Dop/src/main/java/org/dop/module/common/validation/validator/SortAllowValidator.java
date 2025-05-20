package org.dop.module.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dop.module.common.validation.anotation.SortAllow;

import java.util.Arrays;

public class SortAllowValidator implements ConstraintValidator<SortAllow, String> {

    private SortAllow constraintAnnotation;

    @Override
    public void initialize(SortAllow constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String[] sortField = constraintAnnotation.sortFields();
        return Arrays.asList(sortField).contains(value);
    }
}
