package com.kigen.retail_store.services.product;

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
import com.kigen.retail_store.dtos.product.ProductBrandDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.product.EProductBrand;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.product.ProductBrandDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SProductBrand implements IProductBrand {

    @Autowired
    private ProductBrandDAO productBrandDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IClient sClient;

    @Autowired
    private IUserDetails sUserDetails;

    @Override
    public Boolean checkExistsByName(String name) {
        return productBrandDAO.existsByName(name);
    }

    @Override
    public Boolean checkIsOwner(Integer productBrandId) {
        EProductBrand productBrand = getById(productBrandId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == productBrand.getClient();
    }

    @Override
    public EProductBrand create(ProductBrandDTO productBrandDTO) {

        EProductBrand productBrand = new EProductBrand();
        setClient(productBrand, productBrandDTO.getClientId());
        productBrand.setCreatedOn(LocalDateTime.now());
        productBrand.setDescription(productBrandDTO.getDescription());
        productBrand.setName(productBrandDTO.getName());

        save(productBrand);
        return productBrand;
    }

    @Override
    public void delete(Integer productBrandId) {
        EProductBrand productBrand = getById(productBrandId, true);
        productBrandDAO.delete(productBrand);
    }

    @Override
    public Optional<EProductBrand> getById(Integer productBrandId) {
        return productBrandDAO.findById(productBrandId);
    }

    @Override
    public EProductBrand getById(Integer productBrandId, Boolean handleException) {
        Optional<EProductBrand> productBrand = getById(productBrandId);
        if (!productBrand.isPresent() && handleException) {
            throw new NotFoundException("product brand with specified id not found", "productBrandId");
        }
        return productBrand.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EProductBrand> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EProductBrand> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EProductBrand>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EProductBrand> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productBrandDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EProductBrand productBrand) {
        productBrandDAO.save(productBrand);
    }

    private void setClient(EProductBrand productBrand, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            productBrand.setClient(client);
        }
    }

    @Override
    public EProductBrand update(Integer productBrandId, ProductBrandDTO productBrandDTO) {

        EProductBrand productBrand = getById(productBrandId, true);
        if (productBrandDTO.getDescription() != null) {
            productBrand.setDescription(productBrandDTO.getDescription());
        }
        if (productBrandDTO.getName() != null) {
            productBrand.setName(productBrandDTO.getName());
        }

        save(productBrand);
        return productBrand;
    }
    
}
