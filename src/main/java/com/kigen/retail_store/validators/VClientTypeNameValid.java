package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsClientTypeNameValid;
import com.kigen.retail_store.services.client.IClientType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VClientTypeNameValid implements ConstraintValidator<IsClientTypeNameValid, String> {

    @Autowired
    private IClientType sClientType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sClientType.checkExistsByName(value);
    }
    
}
