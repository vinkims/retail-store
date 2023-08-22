package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsRegionNameValid;
import com.kigen.retail_store.services.address.IRegion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VRegionNameValid implements ConstraintValidator<IsRegionNameValid, String> {

    @Autowired
    private IRegion sRegion;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sRegion.checkExistsByName(value);
    }
    
}
