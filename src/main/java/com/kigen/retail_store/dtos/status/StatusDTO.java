package com.kigen.retail_store.dtos.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.annotations.IsStatusNameValid;
import com.kigen.retail_store.models.status.EStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class StatusDTO {
    
    private Integer id;

    @IsStatusNameValid
    private String name;

    private String description;

    public StatusDTO(EStatus status) {
        setDescription(status.getDescription());
        setId(status.getId());
        setName(status.getName());
    }
}
