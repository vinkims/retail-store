package com.kigen.retail_store.services.user;

import com.kigen.retail_store.dtos.user.UserAddressDTO;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserAddress;

public interface IUserAddress {
    
    EUserAddress create(EUser user, UserAddressDTO userAddressDTO);

    void removeDefault(Integer userId);

    void save(EUserAddress userAddress);

    void setDefault(Integer userId, Integer addressId);
}
