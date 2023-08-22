package com.kigen.retail_store.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.role.RoleDTO;
import com.kigen.retail_store.models.user.EUserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserRoleDTO {
    
    private RoleDTO role;

    private Integer roleId;

    public UserRoleDTO(EUserRole userRole) {
        setRole(new RoleDTO(userRole.getRole()));
    }
}
