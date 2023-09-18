package com.kigen.retail_store.services.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ConfigTypeDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.product.EConfigType;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.product.ConfigTypeDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SConfigType implements IConfigType {

    @Autowired
    private ConfigTypeDAO configTypeDAO;

    @Autowired
    private IClient sClient;

    @Autowired
    private IUserDetails sUserDetails;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkIsOwner(Integer configTypeId) {
        EConfigType configType = getById(configTypeId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == configType.getClient();
    }

    @Override
    public EConfigType create(ConfigTypeDTO configTypeDTO) {

        EConfigType configType = new EConfigType();
        setClient(configType, configTypeDTO.getClientId());
        configType.setDescription(configTypeDTO.getDescription());
        configType.setName(configTypeDTO.getName());

        save(configType);
        return configType;
    }

    @Override
    public void delete(Integer configTypeId) {
        EConfigType configType = getById(configTypeId, true);
        configTypeDAO.delete(configType);
    }

    @Override
    public Optional<EConfigType> getById(Integer configTypeId) {
        return configTypeDAO.findById(configTypeId);
    }

    @Override
    public EConfigType getById(Integer configTypeId, Boolean handleException) {
        Optional<EConfigType> configType = getById(configTypeId);
        if (!configType.isPresent() && handleException) {
            throw new NotFoundException("config type with specified id not found", "configTypeId");
        }
        return configType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EConfigType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EConfigType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EConfigType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EConfigType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return configTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EConfigType configType) {
        configTypeDAO.save(configType);
    }

    private void setClient(EConfigType configType, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            configType.setClient(client);
        }
    }

    @Override
    public EConfigType update(Integer configTypeId, ConfigTypeDTO configTypeDTO) {

        EConfigType configType = getById(configTypeId, true);
        if (configTypeDTO.getDescription() != null) {
            configType.setDescription(configTypeDTO.getDescription());
        }
        if (configTypeDTO.getName() != null) {
            configType.setName(configTypeDTO.getName());
        }

        save(configType);
        return configType;
    }
    
}
