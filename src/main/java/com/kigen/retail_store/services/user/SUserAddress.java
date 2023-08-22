package com.kigen.retail_store.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.user.UserAddressDTO;
import com.kigen.retail_store.models.address.EAddress;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserAddress;
import com.kigen.retail_store.repositories.user.UserAddressDAO;
import com.kigen.retail_store.services.address.IAddress;

@Service
public class SUserAddress implements IUserAddress {

    Logger logger = LoggerFactory.getLogger(SUserAddress.class);

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private IAddress sAddress;

    @Override
    public EUserAddress create(EUser user, UserAddressDTO userAddressDTO) {

        EAddress address = sAddress.getById(userAddressDTO.getAddressId(), true);

        EUserAddress userAddress = new EUserAddress(user, address);
        userAddress.setIsDefault(userAddressDTO.getIsDefault());

        save(userAddress);
        return userAddress;
    }

    @Override
    public void removeDefault(Integer userId) {
        userAddressDAO.removeDefault(userId);
    }

    @Override
    public void save(EUserAddress userAddress) {
        userAddressDAO.save(userAddress);
    }

    @Override
    public void setDefault(Integer userId, Integer addressId) {
        try {
            removeDefault(userId);
            userAddressDAO.setDefault(userId, addressId);
        } catch (Exception ex) {
            logger.error("\n[LOCATION] - SUserAddress.setDefault\n[MSG] {}", ex.getMessage());
        }
    }
    
}
