package com.kigen.retail_store.services.status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.status.EStatus;
import com.kigen.retail_store.repositories.status.StatusDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SStatus implements IStatus {

    @Autowired
    private StatusDAO statusDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return statusDAO.existsByName(name);
    }

    @Override
    public EStatus create(StatusDTO statusDTO) {
        EStatus status = new EStatus();
        status.setDescription(statusDTO.getDescription());
        status.setName(statusDTO.getName());

        save(status);
        return status;
    }

    @Override
    public Optional<EStatus> getById(Integer statusId) {
        return statusDAO.findById(statusId);
    }

    @Override
    public EStatus getById(Integer statusId, Boolean handleException) {
        Optional<EStatus> status = getById(statusId);
        if (!status.isPresent() && handleException) {
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        return status.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EStatus> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EStatus> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EStatus>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EStatus> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return statusDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EStatus status) {
        statusDAO.save(status);
    }
    
}
