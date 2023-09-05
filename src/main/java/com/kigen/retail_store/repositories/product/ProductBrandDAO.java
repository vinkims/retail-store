package com.kigen.retail_store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.product.EProductBrand;

public interface ProductBrandDAO extends JpaRepository<EProductBrand, Integer>, JpaSpecificationExecutor<EProductBrand> {
    
    Boolean existsByName(String name);
}
