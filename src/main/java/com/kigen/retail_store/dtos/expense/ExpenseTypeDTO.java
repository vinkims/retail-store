package com.kigen.retail_store.dtos.expense;

import com.kigen.retail_store.annotations.IsExpenseTypeNameValid;
import com.kigen.retail_store.models.EExpenseType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpenseTypeDTO {
    
    private Integer id;

    @IsExpenseTypeNameValid
    private String name;

    private String description;

    public ExpenseTypeDTO(EExpenseType expenseType) {
        setDescription(expenseType.getDescription());
        setId(expenseType.getId());
        setName(expenseType.getName());
    }
}
