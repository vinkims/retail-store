package com.kigen.retail_store.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.payment.TransactionTypeDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.ETransactionType;
import com.kigen.retail_store.repositories.TransactionTypeDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class STransactionType implements ITransactionType {

    @Autowired
    private TransactionTypeDAO transactionTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return transactionTypeDAO.existsByName(name);
    }

    @Override
    public ETransactionType create(TransactionTypeDTO transactionTypeDTO) {
        ETransactionType transactionType = new ETransactionType();
        transactionType.setDescription(transactionTypeDTO.getDescription());
        transactionType.setName(transactionTypeDTO.getName());

        save(transactionType);
        return transactionType;
    }

    @Override
    public Optional<ETransactionType> getById(Integer transactionTypeId) {
        return transactionTypeDAO.findById(transactionTypeId);
    }

    @Override
    public ETransactionType getById(Integer transactiontypeId, Boolean handleException) {
        Optional<ETransactionType> transactionType = getById(transactiontypeId);
        if (!transactionType.isPresent() && handleException) {
            throw new NotFoundException("transaction type with specified id not found", "transactionTypeId");
        }
        return transactionType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ETransactionType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ETransactionType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ETransactionType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transactionTypeDAO.findAll(spec, pageRequest);

    }

    @Override
    public void save(ETransactionType transactionType) {
        transactionTypeDAO.save(transactionType);
    }
    
}
