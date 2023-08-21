package com.kigen.retail_store.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.user.ERole;

public interface RoleDAO extends JpaRepository<ERole, Integer>, JpaSpecificationExecutor<ERole> {
    
    Boolean existsByName(String name);
}
