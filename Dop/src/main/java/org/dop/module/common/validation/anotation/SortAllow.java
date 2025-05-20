package org.dop.module.common.validation.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dop.module.common.validation.validator.SortAllowValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortAllowValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortAllow {
    
    String[] sortFields() default {};
    
    String message() default "Invalid sort field.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
