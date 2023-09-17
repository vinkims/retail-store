package com.kigen.retail_store.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.models.product.EConfigType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ConfigTypeDTO {
    
    private Integer id;

    private ClientDTO client;

    private Integer clientId;

    private String name;

    private String description;

    public ConfigTypeDTO(EConfigType configType) {
        if (configType.getClient() != null) {
            setClient(new ClientDTO(configType.getClient()));
        }
        setDescription(configType.getDescription());
        setId(configType.getId());
        setName(configType.getName());
    }
}
