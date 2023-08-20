package com.kigen.retail_store.services.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.client.ClientTypeDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.client.EClientType;

public interface IClientType {
    
    Boolean checkExistsByName(String name);

    EClientType create(ClientTypeDTO clientTypeDTO);

    Optional<EClientType> getById(Integer clientTypeId);

    EClientType getById(Integer clientTypeId, Boolean handleException);

    Page<EClientType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EClientType clientType);
}
