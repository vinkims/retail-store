package com.kigen.retail_store.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.models.product.EProductItemConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ProductItemConfigDTO {
    
    private ConfigTypeDTO configType;

    private Integer configTypeId;

    private String value;

    public ProductItemConfigDTO(EProductItemConfig productItemConfig) {
        if (productItemConfig.getConfigType() != null) {
            setConfigType(new ConfigTypeDTO(productItemConfig.getConfigType()));
        }
        setValue(productItemConfig.getValue());
    }
}
