package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsStatusNameValid;
import com.kigen.retail_store.services.status.IStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VStatusNameValid implements ConstraintValidator<IsStatusNameValid, String> {
    
    @Autowired
    private IStatus sStatus;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sStatus.checkExistsByName(value);
    }

}
