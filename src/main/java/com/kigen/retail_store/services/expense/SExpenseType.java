package com.kigen.retail_store.services.expense;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.expense.ExpenseTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.EExpenseType;
import com.kigen.retail_store.repositories.ExpenseTypeDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SExpenseType implements IExpenseType {

    @Autowired
    private ExpenseTypeDAO expenseTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return expenseTypeDAO.existsByName(name);
    }

    @Override
    public EExpenseType create(ExpenseTypeDTO expenseTypeDTO) {
        EExpenseType expenseType = new EExpenseType();
        expenseType.setDescription(expenseTypeDTO.getDescription());
        expenseType.setName(expenseTypeDTO.getName());

        save(expenseType);
        return expenseType;
    }

    @Override
    public Optional<EExpenseType> getById(Integer expenseTypeId) {
        return expenseTypeDAO.findById(expenseTypeId);
    }

    @Override
    public EExpenseType getById(Integer expenseTypeId, Boolean handleException) {
        Optional<EExpenseType> expenseType = getById(expenseTypeId);
        if (!expenseType.isPresent() && handleException) {
            throw new NotFoundException("expense type with specified id not found", "expenseTypeId");
        }
        return expenseType.get();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Page<EExpenseType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EExpenseType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EExpenseType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EExpenseType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return expenseTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EExpenseType expenseType) {
        expenseTypeDAO.save(expenseType);
    }
    
}
