package com.example.ExpenseService.Validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<ValidateCategory, String> {

    @Override
    public boolean isValid(String categoryType, ConstraintValidatorContext constraintValidatorContext) {
        List<String> categoryTypes = Arrays.asList("Shopping", "food", "travelling", "salary", "other");

        return categoryTypes.contains(categoryType);
    }
}
