package com.kigen.retail_store.models.user;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class UserAddressPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "user_id")
    private Integer userId;

    public UserAddressPK(Integer userId, Integer addressId) {
        setAddressId(addressId);
        setUserId(userId);
    }
}
