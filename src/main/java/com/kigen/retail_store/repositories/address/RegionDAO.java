package com.kigen.retail_store.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.address.ERegion;

public interface RegionDAO extends JpaRepository<ERegion, Integer>, JpaSpecificationExecutor<ERegion> {
    
    Boolean existsByName(String name);
}
