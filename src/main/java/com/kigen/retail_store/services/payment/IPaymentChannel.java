package com.kigen.retail_store.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.payment.PaymentChannelDTO;
import com.kigen.retail_store.models.EPaymentChannel;

public interface IPaymentChannel {
    
    Boolean checkExistsByName(String name);

    EPaymentChannel create(PaymentChannelDTO paymentChannelDTO);

    Optional<EPaymentChannel> getById(Integer paymentChannelId);

    EPaymentChannel getById(Integer paymentChannelId, Boolean handleException);

    Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EPaymentChannel paymentChannel);
}
