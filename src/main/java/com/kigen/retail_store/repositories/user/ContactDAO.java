package com.kigen.retail_store.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.retail_store.models.user.EContact;

public interface ContactDAO extends JpaRepository<EContact, String> {
    
    Boolean existsByValue(String value);
}
