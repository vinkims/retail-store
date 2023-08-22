package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsCountryNameValid;
import com.kigen.retail_store.services.address.ICountry;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VCountryNameValid implements ConstraintValidator <IsCountryNameValid, String> {

    @Autowired
    private ICountry sCountry;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sCountry.checkExistsByName(value);
    }
    
}
