package com.kigen.retail_store.dtos.payment;

import com.kigen.retail_store.annotations.IsTransactionTypeNameValid;
import com.kigen.retail_store.models.ETransactionType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionTypeDTO {
    
    private Integer id;

    @IsTransactionTypeNameValid
    private String name;

    private String description;

    public TransactionTypeDTO(ETransactionType transactionType) {
        setDescription(transactionType.getDescription());
        setId(transactionType.getId());
        setName(transactionType.getName());
    }
}
