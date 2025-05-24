package org.dop.module.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dop.module.common.validation.anotation.AllowSortName;

import java.util.Arrays;

public class AllowSortNameValidator implements ConstraintValidator<AllowSortName, String> {

    private AllowSortName constraintAnnotation;

    @Override
    public void initialize(AllowSortName constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String[] allowSortFields = constraintAnnotation.allowSortFields();
        return Arrays.asList(allowSortFields).contains(value);
    }
}
