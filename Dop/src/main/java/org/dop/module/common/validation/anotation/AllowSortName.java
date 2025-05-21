package org.dop.module.common.validation.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dop.module.common.validation.validator.AllowSortNameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowSortNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowSortName {
    
    String[] allowSortFields() default {};
    
    String message() default "Invalid sort field.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
