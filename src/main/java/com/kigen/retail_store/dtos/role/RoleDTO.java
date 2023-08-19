package com.kigen.retail_store.dtos.role;

import com.kigen.retail_store.annotations.IsRoleNameValid;
import com.kigen.retail_store.models.ERole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    
    private Integer id;

    @IsRoleNameValid
    private String name;

    private String description;

    public RoleDTO(ERole role) {
        setDescription(role.getDescription());
        setId(role.getId());
        setName(role.getName());
    }
}
