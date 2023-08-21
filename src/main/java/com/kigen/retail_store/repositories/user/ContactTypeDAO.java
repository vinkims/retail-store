package com.kigen.retail_store.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.user.EContactType;

public interface ContactTypeDAO extends JpaRepository<EContactType, Integer>, JpaSpecificationExecutor<EContactType> {
    
    Boolean existsByName(String name);
}
