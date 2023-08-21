package com.kigen.retail_store.controllers.user;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.role.RoleDTO;
import com.kigen.retail_store.models.user.ERole;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.role.IRole;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/role")
public class CRole {
    
    @Autowired
    private IRole sRole;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException {

        ERole role = sRole.create(roleDTO);

        return ResponseEntity
            .created(new URI("/" + role.getId()))
            .body(new SuccessResponse(201, "successfully created role", new RoleDTO(role)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getRolesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<ERole> roles = sRole.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched roles", roles, 
                RoleDTO.class, ERole.class));
    }

    @GetMapping(path = "/{roleId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getRole(@PathVariable Integer roleId) {

        ERole role = sRole.getById(roleId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched role", new RoleDTO(role)));
    }

    @PatchMapping(path = "/{roleId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateRole(@PathVariable Integer roleId, @Valid @RequestBody RoleDTO roleDTO) {

        ERole role = sRole.update(roleId, roleDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated role", new RoleDTO(role)));
    }
}
