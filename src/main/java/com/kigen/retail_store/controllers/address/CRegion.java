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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.address.RegionDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.ERegion;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.address.IRegion;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/region")
public class CRegion {
    
    @Autowired
    private IRegion sRegion;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRegion(@Valid @RequestBody RegionDTO regionDTO) throws URISyntaxException {

        ERegion region = sRegion.create(regionDTO);

        return ResponseEntity
            .created(new URI("/" + region.getId()))
            .body(new SuccessResponse(201, "successfully created region", new RegionDTO(region)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getRegionsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        List<String> allowedFields = new ArrayList<>(Arrays.asList("country.id", "country.name"));

        Page<ERegion> regions = sRegion.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched regions", regions, 
                RegionDTO.class, ERegion.class));
    }

    @GetMapping(path = "/{regionId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getRegion(@PathVariable Integer regionId) {

        ERegion region = sRegion.getById(regionId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched region", new RegionDTO(region)));
    }
}
