package com.kigen.retail_store.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.address.RegionDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.address.ECountry;
import com.kigen.retail_store.models.address.ERegion;
import com.kigen.retail_store.repositories.address.RegionDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SRegion implements IRegion {

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private ICountry sCountry;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return regionDAO.existsByName(name);
    }

    @Override
    public ERegion create(RegionDTO regionDTO) {
        
        ERegion region = new ERegion();
        setCountry(region, regionDTO.getCountryId());
        region.setName(regionDTO.getName());

        save(region);
        return region;
    }

    @Override
    public Optional<ERegion> getById(Integer regionId) {
        return regionDAO.findById(regionId);
    }

    @Override
    public ERegion getById(Integer regionId, Boolean handleException) {
        Optional<ERegion> region = getById(regionId);
        if (!region.isPresent() && handleException) {
            throw new NotFoundException("region with specified id not found", "regionId");
        }
        return region.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ERegion> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ERegion> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ERegion>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ERegion> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return regionDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ERegion region) {
        regionDAO.save(region);
    }

    private void setCountry(ERegion region, Integer countryId) {
        
        if (countryId != null) {
            ECountry country = sCountry.getById(countryId, true);
            region.setCountry(country);
        }
    }
    
}
