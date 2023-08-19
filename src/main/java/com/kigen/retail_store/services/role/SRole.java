package com.kigen.retail_store.services.role;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.role.RoleDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.ERole;
import com.kigen.retail_store.repositories.RoleDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SRole implements IRole {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return roleDAO.existsByName(name);
    }

    @Override
    public ERole create(RoleDTO roleDTO) {
        ERole role = new ERole();
        role.setDescription(roleDTO.getDescription());
        role.setName(roleDTO.getName());

        save(role);
        return role;
    }

    @Override
    public Optional<ERole> getById(Integer roleId) {
        return roleDAO.findById(roleId);
    }

    @Override
    public ERole getById(Integer roleId, Boolean handleException) {
        Optional<ERole> role = getById(roleId);
        if (!role.isPresent() && handleException) {
            throw new NotFoundException("role with specified id not found", "roleId");
        }
        return role.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ERole> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ERole> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ERole>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ERole> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return roleDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ERole role) {
        roleDAO.save(role);
    }

    @Override
    public ERole update(Integer roleId, RoleDTO roleDTO) {

        ERole role = getById(roleId, true);
        if (roleDTO.getDescription() != null) {
            role.setDescription(roleDTO.getDescription());
        }
        if (roleDTO.getName() != null) {
            role.setName(roleDTO.getName());
        }

        save(role);
        return role;
    }
    
}
