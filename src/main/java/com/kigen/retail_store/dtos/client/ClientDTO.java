package com.kigen.retail_store.dtos.client;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.annotations.IsClientNameValid;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.client.EClient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ClientDTO {
    
    private Integer id;

    @IsClientNameValid
    private String name;

    private String clientCode;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private ClientTypeDTO clientType;

    private Integer clientTypeId;

    private StatusDTO status;

    private Integer statusId;

    public ClientDTO(EClient client) {
        setClientCode(client.getClientCode());
        if (client.getClientType() != null) {
            setClientType(new ClientTypeDTO(client.getClientType()));
        }
        setCreatedOn(client.getCreatedOn());
        setId(client.getId());
        setModifiedOn(client.getModifiedOn());
        setName(client.getName());
        if (client.getStatus() != null) {
            setStatus(new StatusDTO(client.getStatus()));
        }
    }
}
