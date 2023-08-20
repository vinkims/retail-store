package com.kigen.retail_store.controllers.client;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.client.IClient;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/client")
public class CClient {
    
    @Autowired
    private IClient sClient;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createClient(@Valid @RequestBody ClientDTO clientDTO) throws URISyntaxException {

        EClient client = sClient.create(clientDTO);

        return ResponseEntity
            .created(new URI("/" + client.getId()))
            .body(new SuccessResponse(201, "successfully created client", new ClientDTO(client)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getClientsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "clientCode", "clientType.id", "status.id", "updatedOn", "name"));
        
        Page<EClient> clients = sClient.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched clients", clients, 
                ClientDTO.class, EClient.class));
    }

    @GetMapping(path = "/{clientId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getClient(@PathVariable Integer clientId) {

        EClient client = sClient.getById(clientId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched client", new ClientDTO(client)));
    }

    @PatchMapping(path = "/{clientId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateClient(@PathVariable Integer clientId, @Valid @RequestBody ClientDTO clientDTO) {

        EClient client = sClient.update(clientId, clientDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated client", new ClientDTO(client)));
    }
}
