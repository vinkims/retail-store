package com.kigen.retail_store.controllers.product;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ConfigTypeDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EConfigType;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IConfigType;

@RestController
@RequestMapping(path = "/config/type")
public class CConfigType {
    
    @Autowired
    private IConfigType sConfigType;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createConfigType(@RequestBody ConfigTypeDTO configTypeDTO) throws URISyntaxException {

        EUser user = sUserDetails.getActiveUserByContact();
        if (user.getClient() != null) {
            configTypeDTO.setClientId(user.getClient().getId());
        }

        EConfigType configType = sConfigType.create(configTypeDTO);

        return ResponseEntity
            .created(new URI("/" + configType.getId()))
            .body(new SuccessResponse(201, "successfully created config type", new ConfigTypeDTO(configType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getConfigTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (!sUserDetails.checkIsSystemAdmin()) {
            EUser user = sUserDetails.getActiveUserByContact();
            if (user.getClient() != null) {
                String searchQuery = String.format(",client.idEQ%s", user.getClient().getId());
                pageDTO.setSearch(pageDTO.getSearch() + searchQuery);
            }
        }

        List<String> allowedFields = new ArrayList<>(Arrays.asList("client.id", "client.clientCode", "name"));

        Page<EConfigType> configTypes = sConfigType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched config types", configTypes, 
                ConfigTypeDTO.class, EConfigType.class));
    }

    @GetMapping(path = "/{configTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getConfigType(@PathVariable Integer configTypeId) {

        checkOwner(configTypeId);

        EConfigType configType = sConfigType.getById(configTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched config type", new ConfigTypeDTO(configType)));
    }

    @PatchMapping(path = "/{configTypeId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateConfigType(@PathVariable Integer configTypeId, @RequestBody ConfigTypeDTO configTypeDTO) {

        checkOwner(configTypeId);

        EConfigType configType = sConfigType.update(configTypeId, configTypeDTO);

        return ResponseEntity
        .ok()
        .body(new SuccessResponse(200, "successfully updated config type", new ConfigTypeDTO(configType)));
    }

    @DeleteMapping(path = "/{configTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteConfigType(@PathVariable Integer configTypeId) {

        checkOwner(configTypeId);

        sConfigType.delete(configTypeId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted config type", ""));
    }

    private void checkOwner(Integer configTypeId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sConfigType.checkIsOwner(configTypeId)) {
            throw new InvalidInputException("Sorry, client does not match requester", "clientId");
        }
    }
}
