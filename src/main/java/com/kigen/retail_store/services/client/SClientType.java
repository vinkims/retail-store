package com.kigen.retail_store.services.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.client.ClientTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.EClientType;
import com.kigen.retail_store.repositories.ClientTypeDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SClientType implements IClientType {

    @Autowired
    private ClientTypeDAO clientTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return clientTypeDAO.existsByName(name);
    }

    @Override
    public EClientType create(ClientTypeDTO clientTypeDTO) {
        EClientType clientType = new EClientType();
        clientType.setDescription(clientTypeDTO.getDescription());
        clientType.setName(clientTypeDTO.getName());

        save(clientType);
        return clientType;
    }

    @Override
    public Optional<EClientType> getById(Integer clientTypeId) {
        return clientTypeDAO.findById(clientTypeId);
    }

    @Override
    public EClientType getById(Integer clientTypeId, Boolean handleException) {
        Optional<EClientType> clientType = getById(clientTypeId);
        if (!clientType.isPresent() && handleException) {
            throw new NotFoundException("client type with specified id not found", "clientTypeId");
        }
        return clientType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EClientType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EClientType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EClientType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EClientType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return clientTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EClientType clientType) {
        clientTypeDAO.save(clientType);
    }
    
}
