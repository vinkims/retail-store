package com.kigen.retail_store.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.address.ECountry;

public interface CountryDAO extends JpaRepository<ECountry, Integer>, JpaSpecificationExecutor<ECountry> {
    
    Boolean existsByName(String name);
}
