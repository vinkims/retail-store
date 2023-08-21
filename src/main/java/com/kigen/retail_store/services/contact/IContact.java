package com.kigen.retail_store.services.contact;

import java.util.Optional;

import com.kigen.retail_store.dtos.contact.ContactDTO;
import com.kigen.retail_store.models.user.EContact;
import com.kigen.retail_store.models.user.EUser;

public interface IContact {
    
    Boolean checkExistsByValue(String value);

    EContact create(EUser user, ContactDTO contactDTO);

    Optional<EContact> getByValue(String value);

    EContact getByValue(String value, Boolean handleException);

    void save(EContact contact);
}
