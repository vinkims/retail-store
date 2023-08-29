package com.kigen.retail_store.dtos.product;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.models.product.EProductCategory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ProductCategoryDTO {
    
    private Integer id;

    private String name;

    private LocalDateTime createdOn;

    private String description;

    private ClientDTO client;

    private Integer clientId;

    private ProductCategoryDTO parentCategory;

    private Integer parentCategoryId;

    public ProductCategoryDTO(EProductCategory productCategory) {
        if (productCategory.getClient() != null) {
            setClient(new ClientDTO(productCategory.getClient()));
        }
        setCreatedOn(productCategory.getCreatedOn());
        setDescription(productCategory.getDescription());
        setId(productCategory.getId());
        setName(productCategory.getName());
        if (productCategory.getParentCategory() != null) {
            setParentCategory(new ProductCategoryDTO(productCategory.getParentCategory()));
        }
    }
}
