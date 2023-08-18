package com.kigen.retail_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.EPaymentChannel;

public interface PaymentChannelDAO extends JpaRepository<EPaymentChannel, Integer>, JpaSpecificationExecutor<EPaymentChannel> {
    
    Boolean existsByName(String name);
}
