package com.kigen.retail_store.services.user;

import java.util.Optional;

import com.kigen.retail_store.models.user.ERole;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserRole;

public interface IUserRole {
    
    EUserRole create(EUser user, ERole role);

    Optional<EUserRole> getByUserIdAndRoleId(Integer userId, Integer roleId);

    void save(EUserRole userRole);

    void updateUserRole(Integer userId, Integer roleId);
}
