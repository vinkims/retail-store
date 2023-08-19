package com.kigen.retail_store.controllers.payment;

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
import com.kigen.retail_store.dtos.payment.TransactionTypeDTO;
import com.kigen.retail_store.models.ETransactionType;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.payment.ITransactionType;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/transaction/type")
public class CTransactionType {
    
    @Autowired
    private ITransactionType sTransactionType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransactionType(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws URISyntaxException {

        ETransactionType transactionType = sTransactionType.create(transactionTypeDTO);

        return ResponseEntity
            .created(new URI("/" + transactionType.getId()))
            .body(new SuccessResponse(201, "successfully created transaction type", new TransactionTypeDTO(transactionType)));
    }

    @GetMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getTransactionTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<ETransactionType> transactionTypes = sTransactionType.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched transaction types", transactionTypes, 
                TransactionTypeDTO.class, ETransactionType.class));
    }

    @GetMapping(path = "/{transactionTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransactionType(@PathVariable Integer transactionTypeId) {

        ETransactionType transactionType = sTransactionType.getById(transactionTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched transaction types", new TransactionTypeDTO(transactionType)));
    }
}
