package com.kigen.retail_store.dtos.contact;

import java.time.LocalDateTime;

import com.kigen.retail_store.models.user.EContact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {
    
    private ContactTypeDTO contactType;

    private Integer contactTypeId;

    private LocalDateTime createdOn;

    private String value;

    public ContactDTO(EContact contact) {
        setContactType(new ContactTypeDTO(contact.getContactType()));
        setCreatedOn(contact.getCreatedOn());
        setValue(contact.getValue());
    }
}
