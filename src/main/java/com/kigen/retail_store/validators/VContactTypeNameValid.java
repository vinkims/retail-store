package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsContactTypeNameValid;
import com.kigen.retail_store.services.contact.IContactType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VContactTypeNameValid implements ConstraintValidator<IsContactTypeNameValid, String> {

    @Autowired
    private IContactType sContactType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sContactType.checkExistsByName(value);
    }
    
}
