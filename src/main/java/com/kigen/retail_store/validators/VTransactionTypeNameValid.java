package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsTransactionTypeNameValid;
import com.kigen.retail_store.services.payment.ITransactionType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VTransactionTypeNameValid implements ConstraintValidator<IsTransactionTypeNameValid, String> {

    @Autowired
    private ITransactionType sTransactionType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sTransactionType.checkExistsByName(value);
    }
    
}
