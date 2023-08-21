package com.kigen.retail_store.dtos.user;

import com.kigen.retail_store.dtos.role.RoleDTO;
import com.kigen.retail_store.models.user.EUserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleDTO {
    
    private RoleDTO role;

    private Integer roleId;

    public UserRoleDTO(EUserRole userRole) {
        setRole(new RoleDTO(userRole.getRole()));
    }
}
