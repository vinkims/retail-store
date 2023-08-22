package com.kigen.retail_store.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.address.AddressDTO;
import com.kigen.retail_store.models.user.EUserAddress;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserAddressDTO {
    
    private AddressDTO address;

    private Integer addressId;

    private Boolean isDefault;

    public UserAddressDTO(EUserAddress userAddress) {
        setAddress(new AddressDTO(userAddress.getAddress()));
        setIsDefault(userAddress.getIsDefault());
    }
}
