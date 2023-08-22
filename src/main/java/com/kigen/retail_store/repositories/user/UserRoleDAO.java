package com.kigen.retail_store.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kigen.retail_store.models.user.EUserRole;
import com.kigen.retail_store.models.user.UserRolePK;

public interface UserRoleDAO extends JpaRepository<EUserRole, UserRolePK> {
    
    Optional<EUserRole> findByUserIdAndRoleId(Integer userId, Integer roleId);

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE user_roles SET role_id = :roleId WHERE user_id = :userId",
        nativeQuery = true
    )
    void updateUserRole(Integer userId, Integer roleId);
}
