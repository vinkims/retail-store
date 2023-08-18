package com.kigen.retail_store.dtos.contact;

import com.kigen.retail_store.annotations.IsContactTypeNameValid;
import com.kigen.retail_store.models.EContactType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactTypeDTO {
    
    private Integer id;

    @IsContactTypeNameValid
    private String name;

    private String description;

    private String regexValue;

    public ContactTypeDTO(EContactType contactType) {
        setDescription(contactType.getDescription());
        setId(contactType.getId());
        setName(contactType.getName());
        setRegexValue(contactType.getRegexValue());
    }
}
