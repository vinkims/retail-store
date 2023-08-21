package com.kigen.retail_store.repositories.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.status.EStatus;

public interface StatusDAO extends JpaRepository<EStatus, Integer>, JpaSpecificationExecutor<EStatus> {
    
    Boolean existsByName(String name);
}
