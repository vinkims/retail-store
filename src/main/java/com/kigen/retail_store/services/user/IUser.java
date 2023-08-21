package com.kigen.retail_store.services.user;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.user.UserDTO;
import com.kigen.retail_store.models.user.EUser;

public interface IUser {
    
    EUser create(UserDTO userDTO);

    Optional<EUser> getByContactValue(String contactValue);

    Optional<EUser> getById(Integer userId);

    Optional<EUser> getByIdOrContactValue(String userValue);

    EUser getByIdOrContactValue(String userValue, Boolean handleException);

    Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EUser user);

    EUser update(String userValue, UserDTO userDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
