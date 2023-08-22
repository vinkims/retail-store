package com.kigen.retail_store.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.retail_store.models.user.EBlacklistToken;

public interface BlacklistTokenDAO extends JpaRepository<EBlacklistToken, Integer> {
    
    Boolean existsByTokenHash(Integer tokenHash);
}
