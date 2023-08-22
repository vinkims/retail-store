package com.kigen.retail_store.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.address.CountryDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.address.ECountry;
import com.kigen.retail_store.repositories.address.CountryDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SCountry implements ICountry {

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return countryDAO.existsByName(name);
    }

    @Override
    public ECountry create(CountryDTO countryDTO) {

        ECountry country = new ECountry();
        country.setCode(countryDTO.getCode());
        country.setName(countryDTO.getName());

        save(country);
        return country;
    }

    @Override
    public Optional<ECountry> getById(Integer countryId) {
        return countryDAO.findById(countryId);
    }

    @Override
    public ECountry getById(Integer countryId, Boolean handleException) {
        Optional<ECountry> country = getById(countryId);
        if (!country.isPresent() && handleException) {
            throw new NotFoundException("country with specified id not found", "countryId");
        }
        return country.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECountry> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECountry> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECountry>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ECountry> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return countryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECountry country) {
        countryDAO.save(country);
    }
    
}
