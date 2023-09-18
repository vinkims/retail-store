package com.kigen.retail_store.services.product;

import com.kigen.retail_store.dtos.product.ProductItemConfigDTO;
import com.kigen.retail_store.models.product.EProductItem;
import com.kigen.retail_store.models.product.EProductItemConfig;

public interface IProductItemConfig {
    
    EProductItemConfig create(EProductItem productItem, ProductItemConfigDTO productItemConfigDTO);

    void save(EProductItemConfig productItemConfig);
}
