package com.kigen.retail_store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.retail_store.models.product.EProductItemConfig;
import com.kigen.retail_store.models.product.ProductItemConfigPK;

public interface ProductItemConfigDAO extends JpaRepository<EProductItemConfig, ProductItemConfigPK> {
    
}
