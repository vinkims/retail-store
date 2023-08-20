package com.kigen.retail_store.dtos.client;

import com.kigen.retail_store.annotations.IsClientTypeNameValid;
import com.kigen.retail_store.models.client.EClientType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientTypeDTO {
    
    private Integer id;

    @IsClientTypeNameValid
    private String name;

    private String description;

    public ClientTypeDTO(EClientType clientType) {
        setDescription(clientType.getDescription());
        setId(clientType.getId());
        setName(clientType.getName());
    }
}
