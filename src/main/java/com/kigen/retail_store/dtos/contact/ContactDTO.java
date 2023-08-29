package com.kigen.retail_store.dtos.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.models.user.EContact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ContactDTO {
    
    private ContactTypeDTO contactType;

    private Integer contactTypeId;

    private String value;

    public ContactDTO(EContact contact) {
        setContactType(new ContactTypeDTO(contact.getContactType()));
        setValue(contact.getValue());
    }
}
