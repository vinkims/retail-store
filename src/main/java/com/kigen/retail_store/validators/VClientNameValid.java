package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsClientNameValid;
import com.kigen.retail_store.services.client.IClient;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VClientNameValid implements ConstraintValidator<IsClientNameValid, String> {

    @Autowired
    private IClient sClient;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        return value == null ? true : !sClient.checkExistsByName(value);
    }
    
}
