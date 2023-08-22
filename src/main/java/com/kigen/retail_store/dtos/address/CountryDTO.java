package com.kigen.retail_store.dtos.address;

import com.kigen.retail_store.annotations.IsCountryNameValid;
import com.kigen.retail_store.models.address.ECountry;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryDTO {
    
    private Integer id;

    @IsCountryNameValid
    private String name;

    private String code;

    public CountryDTO(ECountry country) {
        setCode(country.getCode());
        setId(country.getId());
        setName(country.getName());
    }
}
