package com.kigen.retail_store.services.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductTypeDTO;
import com.kigen.retail_store.models.product.EProductType;

public interface IProductType {
    
    Boolean checkExistsByName(String name);

    Boolean checkIsOwner(Integer productTypeId);

    EProductType create(ProductTypeDTO productTypeDTO);

    void delete(Integer productTypeId);

    Optional<EProductType> getById(Integer productTypeId);

    EProductType getById(Integer productTypeId, Boolean handleException);

    Page<EProductType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EProductType productType);

    EProductType update(Integer productTypeId, ProductTypeDTO productTypeDTO);
}
