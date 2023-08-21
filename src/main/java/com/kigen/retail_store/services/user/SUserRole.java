package com.kigen.retail_store.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.models.user.ERole;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserRole;
import com.kigen.retail_store.repositories.user.UserRoleDAO;

@Service
public class SUserRole implements IUserRole {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Override
    public EUserRole create(EUser user, ERole role) {

        EUserRole userRole = new EUserRole(user, role);
        save(userRole);
        return userRole;
    }

    @Override
    public Optional<EUserRole> getByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleDAO.findByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public void save(EUserRole userRole) {
        userRoleDAO.save(userRole);
    }

    @Override
    public void updateUserRole(Integer userId, Integer roleId) {
        userRoleDAO.updateUserRole(userId, roleId);
    }
    
}
