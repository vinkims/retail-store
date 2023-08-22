package com.kigen.retail_store.services.address;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.address.AddressDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.EAddress;

public interface IAddress {
    
    EAddress create(AddressDTO addressDTO);

    Optional<EAddress> getById(Integer addressId);

    EAddress getById(Integer addressId, Boolean handleException);

    Page<EAddress> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EAddress address);

    EAddress update(Integer addressId, AddressDTO addressDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
