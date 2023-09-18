package com.kigen.retail_store.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.product.ProductItemConfigDTO;
import com.kigen.retail_store.models.product.EConfigType;
import com.kigen.retail_store.models.product.EProductItem;
import com.kigen.retail_store.models.product.EProductItemConfig;
import com.kigen.retail_store.repositories.product.ProductItemConfigDAO;

@Service
public class SProductItemConfig implements IProductItemConfig {

    @Autowired
    private IConfigType sConfigType;

    @Autowired
    private ProductItemConfigDAO productItemConfigDAO;

    @Override
    public EProductItemConfig create(EProductItem productItem, ProductItemConfigDTO productItemConfigDTO) {

        EConfigType configType = sConfigType.getById(productItemConfigDTO.getConfigTypeId(), true);

        EProductItemConfig productItemConfig = new EProductItemConfig(productItem, configType);
        productItemConfig.setValue(productItemConfigDTO.getValue());

        save(productItemConfig);
        return productItemConfig;
    }

    @Override
    public void save(EProductItemConfig productItemConfig) {
        productItemConfigDAO.save(productItemConfig);
    }
    
}
