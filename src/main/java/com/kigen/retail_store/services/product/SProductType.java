package com.kigen.retail_store.services.product;

import com.kigen.retail_store.models.user.EUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductTypeDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.product.EProductType;
import com.kigen.retail_store.repositories.product.ProductTypeDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SProductType implements IProductType {

    @Autowired
    private ProductTypeDAO productTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IClient sClient;

    @Autowired
    private IUserDetails sUserDetails;

    @Override
    public Boolean checkExistsByName(String name) {
        return productTypeDAO.existsByName(name);
    }

    @Override
    public Boolean checkIsOwner(Integer productTypeId) {
        EProductType productType = getById(productTypeId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == productType.getClient();
    }

    @Override
    public EProductType create(ProductTypeDTO productTypeDTO) {

        EProductType productType = new EProductType();
        setClient(productType, productTypeDTO.getClientId());
        productType.setCreatedOn(LocalDateTime.now());
        productType.setDescription(productTypeDTO.getDescription());
        productType.setName(productTypeDTO.getName());

        save(productType);
        return productType;
    }

    @Override
    public void delete(Integer productTypeId) {
        EProductType productType = getById(productTypeId, true);
        productTypeDAO.delete(productType);
    }

    @Override
    public Optional<EProductType> getById(Integer productTypeId) {
        return productTypeDAO.findById(productTypeId);
    }

    @Override
    public EProductType getById(Integer productTypeId, Boolean handleException) {
        Optional<EProductType> productType = getById(productTypeId);
        if (!productType.isPresent() && handleException) {
            throw new NotFoundException("product type with specified id not found", "productTypeId");
        }
        return productType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EProductType> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EProductType> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EProductType>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EProductType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EProductType productType) {
        productTypeDAO.save(productType);
    }

    private void setClient(EProductType productType, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            productType.setClient(client);
        }
    }

    @Override
    public EProductType update(Integer productTypeId, ProductTypeDTO productTypeDTO) {

        EProductType productType = getById(productTypeId, true);
        if (productTypeDTO.getDescription() != null) {
            productType.setDescription(productTypeDTO.getDescription());
        }
        if (productTypeDTO.getName() != null) {
            productType.setName(productTypeDTO.getName());
        }

        save(productType);
        return productType;
    }
    
}
