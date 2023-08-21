package com.kigen.retail_store.services.contact;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.contact.ContactDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.user.EContact;
import com.kigen.retail_store.models.user.EContactType;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.user.ContactDAO;

@Service
public class SContact implements IContact {

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private IContactType sContactType;

    @Override
    public Boolean checkExistsByValue(String value) {
        return contactDAO.existsByValue(value);
    }

    @Override
    public EContact create(EUser user, ContactDTO contactDTO) {
        
        EContact contact = new EContact();
        setContactType(contact, contactDTO.getContactTypeId());
        contact.setCreatedOn(LocalDateTime.now());
        contact.setUser(user);
        contact.setValue(contactDTO.getValue());

        save(contact);
        return contact;
    }

    @Override
    public Optional<EContact> getByValue(String value) {
        return contactDAO.findById(value);
    }

    @Override
    public EContact getByValue(String value, Boolean handleException) {
        Optional<EContact> contact = getByValue(value);
        if (!contact.isPresent() && handleException) {
            throw new NotFoundException("contact with specified value does not exist", "contactValue");
        }
        return contact.get();
    }

    @Override
    public void save(EContact contact) {
        contactDAO.save(contact);
    }

    private void setContactType(EContact contact, Integer contactTypeId) {
        if (contactTypeId != null) {
            EContactType contactType = sContactType.getById(contactTypeId, true);
            contact.setContactType(contactType);
        }
    }
    
}
