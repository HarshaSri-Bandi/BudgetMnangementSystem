package com.example.ExpenseService.Validation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Target;


import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CategoryValidator.class)
public @interface ValidateCategory {
    public String message() default "Invalid Category Type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
