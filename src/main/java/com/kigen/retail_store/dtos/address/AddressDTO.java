package com.kigen.retail_store.dtos.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.models.address.EAddress;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AddressDTO {
    
    private Integer id;

    private String name;

    private String street;

    private String addressLineOne;

    private String addressLineTwo;

    private String postalCode;

    private String city;

    private RegionDTO region;

    private Integer regionId;

    public AddressDTO(EAddress address) {
        setAddressLineOne(address.getAddressLineOne());
        setAddressLineTwo(address.getAddressLineOne());
        setCity(address.getCity());
        setId(address.getId());
        setName(address.getName());
        setPostalCode(address.getPostalCode());
        if (address.getRegion() != null) {
            setRegion(new RegionDTO(address.getRegion()));
        }
        setStreet(address.getStreet());
    }
}
