package com.kigen.retail_store.services.address;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.address.AddressDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.address.EAddress;
import com.kigen.retail_store.models.address.ERegion;
import com.kigen.retail_store.repositories.address.AddressDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SAddress implements IAddress {

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private IRegion sRegion;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EAddress create(AddressDTO addressDTO) {

        EAddress address = new EAddress();
        address.setAddressLineOne(addressDTO.getAddressLineOne());
        address.setAddressLineTwo(addressDTO.getAddressLineOne());
        address.setCity(addressDTO.getCity());
        address.setName(addressDTO.getName());
        address.setPostalCode(addressDTO.getPostalCode());
        setRegion(address, addressDTO.getRegionId());
        address.setStreet(addressDTO.getStreet());

        save(address);
        return address;
    }

    @Override
    public Optional<EAddress> getById(Integer addressId) {
        return addressDAO.findById(addressId);
    }

    @Override
    public EAddress getById(Integer addressId, Boolean handleException) {
        Optional<EAddress> address = getById(addressId);
        if (!address.isPresent() && handleException) {
            throw new NotFoundException("address with specified id not found", "addressId");
        }
        return address.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EAddress> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EAddress> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EAddress>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EAddress> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return addressDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EAddress address) {
        addressDAO.save(address);
    }

    private void setRegion(EAddress address, Integer regionId) {
        
        if (regionId != null) {
            ERegion region = sRegion.getById(regionId, true);
            address.setRegion(region);
        }
    }

    @Override
    public EAddress update(Integer addressId, AddressDTO addressDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        EAddress address = getById(addressId, true);

        String[] fields = {"AddressLineOne", "AddressLineTwo", "Street", "Name", "PostalCode", "City"};
        for (String field : fields) {
            Method getField = AddressDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(addressDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EAddress.class.getMethod("set" + field, fieldValue.getClass()).invoke(address, fieldValue);
            }
        }

        setRegion(address, addressDTO.getRegionId());

        save(address);
        return address;
    }
    
}
