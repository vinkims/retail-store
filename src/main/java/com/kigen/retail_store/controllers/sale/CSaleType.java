package com.kigen.retail_store.controllers.sale;

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
import com.kigen.retail_store.dtos.sale.SaleTypeDTO;
import com.kigen.retail_store.models.ESaleType;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.sale.ISaleType;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/sale/type")
public class CSaleType {
    
    @Autowired
    private ISaleType sSaleType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createSaleType(@Valid @RequestBody SaleTypeDTO saleTypeDTO) throws URISyntaxException {

        ESaleType saleType = sSaleType.create(saleTypeDTO);

        return ResponseEntity
            .created(new URI("/" + saleType.getId()))
            .body(new SuccessResponse(201, "successfully created sale type", new SaleTypeDTO(saleType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getSaleTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<ESaleType> saleTypes = sSaleType.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched sale types", saleTypes, 
                SaleTypeDTO.class, ESaleType.class));
    }

    @GetMapping(path = "/{saleTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getSaleType(@PathVariable Integer saleTypeId) {

        ESaleType saleType = sSaleType.getById(saleTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched sale type", new SaleTypeDTO(saleType)));
    }
}
