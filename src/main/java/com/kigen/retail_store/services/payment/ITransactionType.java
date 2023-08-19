package com.kigen.retail_store.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.payment.TransactionTypeDTO;
import com.kigen.retail_store.models.ETransactionType;

public interface ITransactionType {
    
    Boolean checkExistsByName(String name);

    ETransactionType create(TransactionTypeDTO transactionTypeDTO);

    Optional<ETransactionType> getById(Integer transactionTypeId);

    ETransactionType getById(Integer transactiontypeId, Boolean handleException);

    Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ETransactionType transactionType);
}
