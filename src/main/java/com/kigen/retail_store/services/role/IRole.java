package com.kigen.retail_store.services.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.role.RoleDTO;
import com.kigen.retail_store.models.user.ERole;

public interface IRole {
    
    Boolean checkExistsByName(String name);

    ERole create(RoleDTO roleDTO);

    Optional<ERole> getById(Integer roleId);

    ERole getById(Integer roleId, Boolean handleException);

    Page<ERole> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ERole role);

    ERole update(Integer roleId, RoleDTO roleDTO);
}
