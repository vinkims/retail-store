package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsExpenseTypeNameValid;
import com.kigen.retail_store.services.expense.IExpenseType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VExpenseTypeNameValid implements ConstraintValidator<IsExpenseTypeNameValid, String> {

    @Autowired
    IExpenseType sExpenseType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        return value == null ? true : !sExpenseType.checkExistsByName(value);
    }
    
}
