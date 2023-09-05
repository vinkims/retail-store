package com.kigen.retail_store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.product.EProductType;

public interface ProductTypeDAO extends JpaRepository<EProductType, Integer>, JpaSpecificationExecutor<EProductType> {
    
    Boolean existsByName(String name);
}
