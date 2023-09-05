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
import com.kigen.retail_store.dtos.product.ProductCategoryDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.product.EProductCategory;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.product.ProductCategoryDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SProductCategory implements IProductCategory {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private IClient sClient;

    @Autowired
    private IUserDetails sUserDetails;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return productCategoryDAO.existsByName(name);
    }

    @Override
    public Boolean checkIsOwner(Integer productCategoryId) {
        EProductCategory productCategory = getById(productCategoryId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == productCategory.getClient();
    }

    @Override
    public EProductCategory create(ProductCategoryDTO categoryDTO) {
        
        EProductCategory productCategory = new EProductCategory();
        setClient(productCategory, categoryDTO.getClientId());
        productCategory.setCreatedOn(LocalDateTime.now());
        productCategory.setDescription(categoryDTO.getDescription());
        productCategory.setName(categoryDTO.getName());
        setParentCategory(productCategory, categoryDTO.getParentCategoryId());

        save(productCategory);
        return productCategory;
    }

    @Override
    public void delete(Integer productTypeId) {
        EProductCategory productCategory = getById(productTypeId, true);
        productCategoryDAO.delete(productCategory);
    }

    @Override
    public Optional<EProductCategory> getById(Integer categoryId) {
        return productCategoryDAO.findById(categoryId);
    }

    @Override
    public EProductCategory getById(Integer categoryId, Boolean handleException) {
        Optional<EProductCategory> productCategory = getById(categoryId);
        if (!productCategory.isPresent() && handleException) {
            throw new NotFoundException("product category wih specified id not found", "productCategoryId");
        }
        return productCategory.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EProductCategory> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EProductCategory> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EProductCategory>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EProductCategory> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productCategoryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EProductCategory productCategory) {
        productCategoryDAO.save(productCategory);
    }

    private void setClient(EProductCategory productCategory, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            productCategory.setClient(client);
        }
    }

    private void setParentCategory(EProductCategory productCategory, Integer categoryId) {
        if (categoryId != null) {
            EProductCategory parentCategory = getById(categoryId, true);
            productCategory.setParentCategory(parentCategory);
        }
    }

    @Override
    public EProductCategory update(Integer categoryId, ProductCategoryDTO categoryDTO) {

        EProductCategory productCategory = getById(categoryId, true);

        if (categoryDTO.getDescription() != null) {
            productCategory.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getName() != null) {
            productCategory.setName(categoryDTO.getName());
        }
        setParentCategory(productCategory, categoryDTO.getParentCategoryId());

        save(productCategory);
        return productCategory;
    }
    
}
