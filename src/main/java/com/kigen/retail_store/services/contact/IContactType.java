package com.kigen.retail_store.services.contact;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.contact.ContactTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.EContactType;

public interface IContactType {
    
    Boolean checkExistsByName(String name);

    EContactType create(ContactTypeDTO contactTypeDTO);

    Optional<EContactType> getById(Integer contactTypeId);

    EContactType getById(Integer contactTypeId, Boolean handleException);

    Page<EContactType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EContactType contactType);

    EContactType update(Integer contactTypeId, ContactTypeDTO contactTypeDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
