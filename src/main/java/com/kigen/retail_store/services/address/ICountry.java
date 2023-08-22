package com.kigen.retail_store.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.address.CountryDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.ECountry;

public interface ICountry {
    
    Boolean checkExistsByName(String name);

    ECountry create(CountryDTO countryDTO);

    Optional<ECountry> getById(Integer countryId);

    ECountry getById(Integer countryId, Boolean handleException);

    Page<ECountry> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECountry country);
}
