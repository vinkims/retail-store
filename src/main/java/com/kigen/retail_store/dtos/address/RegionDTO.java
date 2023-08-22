package com.kigen.retail_store.dtos.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.annotations.IsRegionNameValid;
import com.kigen.retail_store.models.address.ERegion;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RegionDTO {
    
    private Integer id;

    @IsRegionNameValid
    private String name;

    private CountryDTO country;

    @NotNull
    private Integer countryId;

    public RegionDTO(ERegion region) {
        if (region.getCountry() != null) {
            setCountry(new CountryDTO(region.getCountry()));
        }
        setId(region.getId());
        setName(region.getName());
    }
}
