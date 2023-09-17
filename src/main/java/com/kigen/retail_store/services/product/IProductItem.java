package com.kigen.retail_store.services.product;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductItemDTO;
import com.kigen.retail_store.models.product.EProductItem;

public interface IProductItem {
    
    Boolean checkIsOwner(Integer productItemId);

    EProductItem create(ProductItemDTO productItemDTO);

    void delete(Integer productItemId);

    Optional<EProductItem> getById(Integer productItemId);

    EProductItem getById(Integer productItemId, Boolean handleException);

    Page<EProductItem> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EProductItem productItem);

    EProductItem update(Integer productItemId, ProductItemDTO productItemDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
