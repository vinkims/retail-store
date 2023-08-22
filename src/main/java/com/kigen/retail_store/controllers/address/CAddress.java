package com.kigen.retail_store.controllers.address;

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

import com.kigen.retail_store.dtos.address.AddressDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.EAddress;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.address.IAddress;

@RestController
@RequestMapping(path = "/address")
public class CAddress {
    
    @Autowired
    private IAddress sAddress;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createAddress(@RequestBody AddressDTO addressDTO) throws URISyntaxException {

        EAddress address = sAddress.create(addressDTO);

        return ResponseEntity
            .created(new URI("/" + address.getId()))
            .body(new SuccessResponse(201, "successfully created address", new AddressDTO(address)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getAddressesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>(Arrays.asList("name", "region.id", "region.name", "postalCode", "city", "street"));

        Page<EAddress> addresses = sAddress.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched addresses", addresses, 
                AddressDTO.class, EAddress.class));
    }

    @GetMapping(path = "/{addressId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getAddress(@PathVariable Integer addressId) {

        EAddress address = sAddress.getById(addressId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched address", new AddressDTO(address)));
    }

    @PatchMapping(path = "/{addressid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateAddress(@PathVariable Integer addressId, @RequestBody AddressDTO addressDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EAddress address = sAddress.update(addressId, addressDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated address", new AddressDTO(address)));
    }
}
