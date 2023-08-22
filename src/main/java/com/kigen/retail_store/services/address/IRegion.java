package com.kigen.retail_store.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.address.RegionDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.address.ERegion;

public interface IRegion {
    
    Boolean checkExistsByName(String name);

    ERegion create(RegionDTO regionDTO);

    Optional<ERegion> getById(Integer regionId);

    ERegion getById(Integer regionId, Boolean handleException);

    Page<ERegion> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ERegion region);
}
