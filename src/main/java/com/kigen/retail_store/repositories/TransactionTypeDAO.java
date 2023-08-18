package com.kigen.retail_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.ETransactionType;

public interface TransactionTypeDAO extends JpaRepository<ETransactionType, Integer>, JpaSpecificationExecutor<ETransactionType> {
    
    Boolean existsByName(String name);
}
