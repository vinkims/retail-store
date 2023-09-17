package com.kigen.retail_store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.product.EProductItem;

public interface ProductItemDAO extends JpaRepository<EProductItem, Integer>, JpaSpecificationExecutor<EProductItem> {
    
}
