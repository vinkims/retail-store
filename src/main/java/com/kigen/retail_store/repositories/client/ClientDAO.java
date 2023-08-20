package com.kigen.retail_store.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.retail_store.models.client.EClient;

public interface ClientDAO extends JpaRepository<EClient, Integer>, JpaSpecificationExecutor<EClient> {
    
    Boolean existsByName(String name);
}
