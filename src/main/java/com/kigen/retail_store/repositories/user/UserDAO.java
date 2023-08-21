package com.kigen.retail_store.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kigen.retail_store.models.user.EUser;

public interface UserDAO extends JpaRepository<EUser, Integer>, JpaSpecificationExecutor<EUser> {
    
    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN contacts c ON c.user_id = u.id "
            + "WHERE u.id = :userId "
            + "OR c.value = contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByIdOrContactValue(Integer userId, String contactValue);

    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN contacts c ON c.user_id = u.id "
            + "WHERE c.value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByContactValue(String contactValue);
}
