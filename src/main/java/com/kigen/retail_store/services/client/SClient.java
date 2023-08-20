package com.kigen.retail_store.services.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.EStatus;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.client.EClientType;
import com.kigen.retail_store.repositories.client.ClientDAO;
import com.kigen.retail_store.services.status.IStatus;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SClient implements IClient {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private IClientType sClientType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return clientDAO.existsByName(name);
    }

    @Override
    public EClient create(ClientDTO clientDTO) {

        EClient client = new EClient();
        client.setClientCode(generateCode());
        setClientType(client, clientDTO.getClientTypeId());
        client.setCreatedOn(LocalDateTime.now());
        client.setName(clientDTO.getName());
        setStatus(client, clientDTO.getStatusId());

        save(client);
        return client;
    }

    private String generateCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            builder.append(randomChar);
        }

        String numberStr = String.valueOf((int) (Math.random() * (100_000 - 10_000 + 1) + 10_000));

        String textstr = builder.toString();

        return String.format("%s%s", textstr, numberStr);
    }

    @Override
    public Optional<EClient> getById(Integer clientId) {
        return clientDAO.findById(clientId);
    }

    @Override
    public EClient getById(Integer clientId, Boolean handleException) {
        Optional<EClient> client = getById(clientId);
        if (!client.isPresent() && handleException) {
            throw new NotFoundException("client with specified id not found", "clientId");
        }
        return client.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EClient> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EClient> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EClient>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EClient> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return clientDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EClient client) {
        clientDAO.save(client);
    }

    private void setClientType(EClient client, Integer clientTypeId) {
        if (clientTypeId != null) {
            EClientType clientType = sClientType.getById(clientTypeId, true);
            client.setClientType(clientType);
        }
    }

    private void setStatus(EClient client, Integer statusId) {
        if (statusId != null) {
            EStatus status = sStatus.getById(statusId, true);
            client.setStatus(status);
        }
    } 

    @Override
    public EClient update(Integer clientId, ClientDTO clientDTO) {

        EClient client = getById(clientId, true);
        setClientType(client, clientDTO.getClientTypeId());
        if (clientDTO.getName() != null) {
            client.setName(clientDTO.getName());
        }
        setStatus(client, clientDTO.getStatusId());
        client.setUpdatedOn(LocalDateTime.now());

        save(client);
        return client;
    }
    
}
