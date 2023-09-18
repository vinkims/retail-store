package com.kigen.retail_store.services.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ConfigTypeDTO;
import com.kigen.retail_store.models.product.EConfigType;

public interface IConfigType {
    
    Boolean checkIsOwner(Integer configTypeId);

    EConfigType create(ConfigTypeDTO configTypeDTO);

    void delete(Integer configTypeId);

    Optional<EConfigType> getById(Integer configTypeId);

    EConfigType getById(Integer configTypeId, Boolean handleException);

    Page<EConfigType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EConfigType configType);

    EConfigType update(Integer configTypeId, ConfigTypeDTO configTypeDTO);
}
