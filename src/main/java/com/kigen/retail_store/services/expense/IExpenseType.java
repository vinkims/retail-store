package com.kigen.retail_store.services.expense;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.expense.ExpenseTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.EExpenseType;

public interface IExpenseType {
    
    Boolean checkExistsByName(String name);

    EExpenseType create(ExpenseTypeDTO expenseTypeDTO);

    Optional<EExpenseType> getById(Integer expenseTypeId);

    EExpenseType getById(Integer expenseTypeId, Boolean handleException);

    Page<EExpenseType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EExpenseType expenseType);
}
