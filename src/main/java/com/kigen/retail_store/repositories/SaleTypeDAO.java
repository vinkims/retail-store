package com.kigen.retail_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.ESaleType;

public interface SaleTypeDAO extends JpaRepository<ESaleType, Integer>, JpaSpecificationExecutor<ESaleType> {
    
    Boolean existsByName(String name);
}
