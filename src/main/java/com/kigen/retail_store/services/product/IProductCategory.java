package com.kigen.retail_store.services.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductCategoryDTO;
import com.kigen.retail_store.models.product.EProductCategory;

public interface IProductCategory {
    
    Boolean checkExistsByName(String name);

    Boolean checkIsOwner(Integer productCategoryId)

    EProductCategory create(ProductCategoryDTO categoryDTO);

    Optional<EProductCategory> getById(Integer categoryId);

    EProductCategory getById(Integer categoryId, Boolean handleException);

    Page<EProductCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EProductCategory productCategory);

    EProductCategory update(Integer categoryId, ProductCategoryDTO categoryDTO);
}
