package com.kigen.retail_store.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.annotations.IsPaymentChannelNameValid;
import com.kigen.retail_store.services.payment.IPaymentChannel;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class VPaymentChannelNameValid implements ConstraintValidator<IsPaymentChannelNameValid, String>  {

    @Autowired
    private IPaymentChannel sPaymentChannel;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sPaymentChannel.checkExistsByName(value);
    }
    
}
