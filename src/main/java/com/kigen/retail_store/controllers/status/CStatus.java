package com.kigen.retail_store.controllers.status;

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

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.status.EStatus;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.status.IStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/status")
public class CStatus {
    
    @Autowired
    private IStatus sStatus;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createStatus(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {

        EStatus status = sStatus.create(statusDTO);

        return ResponseEntity
            .created(new URI("/" + status.getId()))
            .body(new SuccessResponse(201, "successfully created status", new StatusDTO(status)));
    }

    @GetMapping(path = "/{statusId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getStatus(@PathVariable Integer statusId) {

        EStatus status = sStatus.getById(statusId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched status", new StatusDTO(status)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getStatusesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<EStatus> statuses = sStatus.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched statuses", statuses, 
                StatusDTO.class, EStatus.class));
    }
}
