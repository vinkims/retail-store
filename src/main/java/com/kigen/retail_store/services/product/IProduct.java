package com.kigen.retail_store.services.product;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductDTO;
import com.kigen.retail_store.models.product.EProduct;

public interface IProduct {
    
    Boolean checkIsOwner(Integer productId);

    EProduct create(ProductDTO productDTO);

    void delete(Integer productId);

    Optional<EProduct> getById(Integer productId);

    EProduct getById(Integer productId, Boolean handleException);

    Page<EProduct> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EProduct product);

    EProduct update(Integer productId, ProductDTO productDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
