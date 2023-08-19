package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsRoleNameValid;
import com.kigen.retail_store.services.role.IRole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VRoleNameValid implements ConstraintValidator<IsRoleNameValid, String> {

    @Autowired
    private IRole sRole;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sRole.checkExistsByName(value);
    }
    
}
