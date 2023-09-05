package com.kigen.retail_store.dtos.product;

import java.time.LocalDateTime;

import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.models.product.EProductType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductTypeDTO {
    
    private Integer id;

    private String name;

    private LocalDateTime createdOn;

    private String description;

    private ClientDTO client;

    private Integer clientId;

    public ProductTypeDTO(EProductType productType) {
        if (productType.getClient() != null) {
            setClient(new ClientDTO(productType.getClient()));
        }
        setCreatedOn(productType.getCreatedOn());
        setDescription(productType.getDescription());
        setId(productType.getId());
        setName(productType.getName());
    }
}
