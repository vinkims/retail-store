package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsSaleTypeNameValid;
import com.kigen.retail_store.services.sale.ISaleType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VSaleTypeNameValid implements ConstraintValidator<IsSaleTypeNameValid, String> {

    @Autowired
    private ISaleType sSaleType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        return value == null ? true : !sSaleType.checkExistsByName(value);
    }
    
}
