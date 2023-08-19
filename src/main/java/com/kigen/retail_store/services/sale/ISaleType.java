package com.kigen.retail_store.services.sale;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.sale.SaleTypeDTO;
import com.kigen.retail_store.models.ESaleType;

public interface ISaleType {
    
    Boolean checkExistsByName(String name);

    ESaleType create(SaleTypeDTO saleTypeDTO);

    Optional<ESaleType> getById(Integer saleTypeId);

    ESaleType getById(Integer saleTypeId, Boolean handleException);

    Page<ESaleType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ESaleType saleType);
}
