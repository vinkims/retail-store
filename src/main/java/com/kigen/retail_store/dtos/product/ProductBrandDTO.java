package com.kigen.retail_store.dtos.product;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.models.product.EProductBrand;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ProductBrandDTO {
    
    private Integer id;

    private String name;

    private LocalDateTime createdOn;

    private String description;

    private ClientDTO client;

    private Integer clientId;

    public ProductBrandDTO(EProductBrand productBrand) {
        if (productBrand.getClient() != null) {
            setClient(new ClientDTO(productBrand.getClient()));
        }
        setCreatedOn(productBrand.getCreatedOn());
        setDescription(productBrand.getDescription());
        setId(productBrand.getId());
        setName(productBrand.getName());
    }
}
