package com.kigen.retail_store.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.address.EAddress;

public interface AddressDAO extends JpaRepository<EAddress, Integer>, JpaSpecificationExecutor<EAddress> {
    
}
