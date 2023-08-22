package com.kigen.retail_store.controllers.address;

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

import com.kigen.retail_store.dtos.address.CountryDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.ECountry;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.address.ICountry;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/country")
public class CCountry {
    
    @Autowired
    private ICountry sCountry;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCountry(@Valid @RequestBody CountryDTO countryDTO) throws URISyntaxException {

        ECountry country = sCountry.create(countryDTO);

        return ResponseEntity
            .created(new URI("/" + country.getId()))
            .body(new SuccessResponse(201, "successfully created country", new CountryDTO(country)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCountriesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<ECountry> countries = sCountry.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched countries", countries, 
                CountryDTO.class, ECountry.class));
    }

    @GetMapping(path = "/{countryId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCountry(@PathVariable Integer countryId) {

        ECountry country = sCountry.getById(countryId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched country", new CountryDTO(country)));
    }
}
