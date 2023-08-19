package com.kigen.retail_store.controllers.expense;

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

import com.kigen.retail_store.dtos.expense.ExpenseTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.EExpenseType;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.expense.IExpenseType;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/expense/type")
public class CExpenseType {
    
    @Autowired
    private IExpenseType sExpenseType;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createExpenseType(@Valid @RequestBody ExpenseTypeDTO expenseTypeDTO) throws URISyntaxException {

        EExpenseType expenseType = sExpenseType.create(expenseTypeDTO);

        return ResponseEntity
            .created(new URI("/" + expenseType.getId()))
            .body(new SuccessResponse(201, "successfully created expense type", new ExpenseTypeDTO(expenseType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getExpenseTypeslist(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<EExpenseType> expenseTypes = sExpenseType.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched expense types", expenseTypes, 
                ExpenseTypeDTO.class, EExpenseType.class));
    }

    @GetMapping(path = "/{expenseTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getExpenseType(@PathVariable Integer expenseTypeId) {

        EExpenseType expenseType = sExpenseType.getById(expenseTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched expense type", new ExpenseTypeDTO(expenseType)));
    }
}
