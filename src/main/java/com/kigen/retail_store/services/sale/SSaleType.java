package com.kigen.retail_store.services.sale;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.sale.SaleTypeDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.ESaleType;
import com.kigen.retail_store.repositories.SaleTypeDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SSaleType implements ISaleType {

    @Autowired
    private SaleTypeDAO saleTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return saleTypeDAO.existsByName(name);
    }

    @Override
    public ESaleType create(SaleTypeDTO saleTypeDTO) {
        ESaleType saleType = new ESaleType();
        saleType.setDescription(saleTypeDTO.getDescription());
        saleType.setName(saleTypeDTO.getName());

        save(saleType);
        return saleType;
    }

    @Override
    public Optional<ESaleType> getById(Integer saleTypeId) {
        return saleTypeDAO.findById(saleTypeId);
    }

    @Override
    public ESaleType getById(Integer saleTypeId, Boolean handleException) {
        Optional<ESaleType> saleType = getById(saleTypeId);
        if (!saleType.isPresent() && handleException) {
            throw new NotFoundException("sale type with specified id not found", "saleTypeId");
        }
        return saleType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ESaleType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ESaleType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ESaleType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ESaleType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return saleTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ESaleType saleType) {
        saleTypeDAO.save(saleType);
    }
    
}
