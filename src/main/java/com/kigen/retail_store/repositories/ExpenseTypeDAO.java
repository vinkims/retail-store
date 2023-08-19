package com.kigen.retail_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.EExpenseType;

public interface ExpenseTypeDAO extends JpaRepository<EExpenseType, Integer>, JpaSpecificationExecutor<EExpenseType> {
    
    Boolean existsByName(String name);
}
