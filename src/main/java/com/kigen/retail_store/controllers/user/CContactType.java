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

import com.kigen.retail_store.dtos.contact.ContactTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.EContactType;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.contact.IContactType;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/contact/type")
public class CContactType {
    
    @Autowired
    private IContactType sContactType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createContactType(@Valid @RequestBody ContactTypeDTO contactTypeDTO) throws URISyntaxException {

        EContactType contactType = sContactType.create(contactTypeDTO);

        return ResponseEntity
            .created(new URI("/" + contactType.getId()))
            .body(new SuccessResponse(201, "siccessfully created contact type", new ContactTypeDTO(contactType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getContactTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<EContactType> contactTypes = sContactType.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched contact types", contactTypes, 
                ContactTypeDTO.class, EContactType.class));
    }

    @GetMapping(path = "/{contactTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getContactType(@PathVariable Integer contactTypeId) {

        EContactType contactType = sContactType.getById(contactTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched contact type", new ContactTypeDTO(contactType)));
    }

    @PatchMapping(path = "/{contactTypeId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateContactType(@PathVariable Integer contactTypeId, @Valid @RequestBody ContactTypeDTO contactTypeDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EContactType contactType = sContactType.update(contactTypeId, contactTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated contact type", new ContactTypeDTO(contactType)));
    }
}
