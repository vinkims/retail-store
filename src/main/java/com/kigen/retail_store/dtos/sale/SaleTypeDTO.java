package com.kigen.retail_store.dtos.sale;

import com.kigen.retail_store.annotations.IsSaleTypeNameValid;
import com.kigen.retail_store.models.ESaleType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleTypeDTO {
    
    private Integer id;

    @IsSaleTypeNameValid
    private String name;

    private String description;

    public SaleTypeDTO(ESaleType saleType) {
        setDescription(saleType.getDescription());
        setId(saleType.getId());
        setName(saleType.getName());
    }
}
