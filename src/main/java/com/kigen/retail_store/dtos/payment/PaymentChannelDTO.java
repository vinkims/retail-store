package com.kigen.retail_store.dtos.payment;

import com.kigen.retail_store.annotations.IsPaymentChannelNameValid;
import com.kigen.retail_store.models.EPaymentChannel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentChannelDTO {
    
    private Integer id;

    @IsPaymentChannelNameValid
    private String name;

    private String description;

    public PaymentChannelDTO(EPaymentChannel paymentChannel) {
        setDescription(paymentChannel.getDescription());
        setId(paymentChannel.getId());
        setName(paymentChannel.getName());
    }
}
