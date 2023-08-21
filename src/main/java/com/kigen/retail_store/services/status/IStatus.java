package com.kigen.retail_store.services.status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.status.EStatus;

public interface IStatus {

    Boolean checkExistsByName(String name);
    
    EStatus create(StatusDTO statusDTO);
    
    Optional<EStatus> getById(Integer statusId);

    EStatus getById(Integer statusId, Boolean handleException);

    Page<EStatus> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EStatus status);
}
