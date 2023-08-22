package com.kigen.retail_store.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kigen.retail_store.models.user.EUserAddress;
import com.kigen.retail_store.models.user.UserAddressPK;

public interface UserAddressDAO extends JpaRepository<EUserAddress, UserAddressPK> {
    
    @Transactional
    @Modifying
    @Query(
        value = "UPDATE user_addresses SET isDefault = false "
            + "WHERE user_id = :userId",
        nativeQuery = true
    )
    void removeDefault(Integer userId);

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE user_addresses SET isDefault = true "
            + "WHERE user_id = :userId "
            + "AND address_id = :addressId",
        nativeQuery = true
    )
    void setDefault(Integer userId, Integer addressId);
}
