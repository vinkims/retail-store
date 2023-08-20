package com.kigen.retail_store.controllers.client;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.client.ClientTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.client.EClientType;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.client.IClientType;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/client/type")
public class CClientType {
    
    @Autowired
    private IClientType sClientType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createClientType(@Valid @RequestBody ClientTypeDTO clientTypeDTO) throws URISyntaxException {

        EClientType clientType = sClientType.create(clientTypeDTO);

        return ResponseEntity
            .created(new URI("/" + clientType.getId()))
            .body(new SuccessResponse(201, "successfully created client type", new ClientTypeDTO(clientType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getClientTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<EClientType> clientTypes = sClientType.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched client types", clientTypes, 
                ClientTypeDTO.class, EClientType.class));
    }

    @GetMapping(path = "/{clientTypeId}")
    public ResponseEntity<SuccessResponse> getClientType(@PathVariable Integer clientId) {

        EClientType clientType = sClientType.getById(clientId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched client type", new ClientTypeDTO(clientType)));
    }
}
