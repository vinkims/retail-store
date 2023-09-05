package com.kigen.retail_store.services.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductBrandDTO;
import com.kigen.retail_store.models.product.EProductBrand;

public interface IProductBrand {
    
    Boolean checkExistsByName(String name);

    Boolean checkIsOwner(Integer productBrandId);

    EProductBrand create(ProductBrandDTO productBrandDTO);

    void delete(Integer productBrandId);

    Optional<EProductBrand> getById(Integer productBrandId);

    EProductBrand getById(Integer productBrandId, Boolean handleException);

    Page<EProductBrand> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EProductBrand productBrand);

    EProductBrand update(Integer productBrandId, ProductBrandDTO productBrandDTO);
}
