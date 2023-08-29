package com.kigen.retail_store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.product.EProductCategory;

public interface ProductCategoryDAO extends JpaRepository<EProductCategory, Integer>, JpaSpecificationExecutor<EProductCategory> {
    
    Boolean existsByName(String name);
}
