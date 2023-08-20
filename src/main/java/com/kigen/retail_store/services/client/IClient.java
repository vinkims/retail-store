package com.kigen.retail_store.services.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.models.client.EClient;

public interface IClient {
    
    Boolean checkExistsByName(String name);

    EClient create(ClientDTO clientDTO);

    Optional<EClient> getById(Integer clientId);

    EClient getById(Integer clientId, Boolean handleException);

    Page<EClient> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EClient client);

    EClient update(Integer clientId, ClientDTO clientDTO);
}
