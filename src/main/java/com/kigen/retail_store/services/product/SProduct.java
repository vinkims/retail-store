package com.kigen.retail_store.services.product;

import com.kigen.retail_store.models.status.EStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.product.EProduct;
import com.kigen.retail_store.models.product.EProductBrand;
import com.kigen.retail_store.models.product.EProductCategory;
import com.kigen.retail_store.models.product.EProductType;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.product.ProductDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.services.status.IStatus;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SProduct implements IProduct {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IClient sClient;

    @Autowired
    private IProductBrand sProductBrand;

    @Autowired
    private IProductCategory sProductCategory;

    @Autowired
    private IProductType sProductType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUserDetails sUserDetails;

    @Override
    public Boolean checkIsOwner(Integer productId) {
        EProduct product = getById(productId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == product.getClient();
    }

    @Override
    public EProduct create(ProductDTO productDTO) {

        EProduct product = new EProduct();
        setClient(product, productDTO.getCientId());
        product.setCreatedOn(LocalDateTime.now());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setName(productDTO.getName());
        setProductBrand(product, productDTO.getProductBrandId());
        setProductCategory(product, productDTO.getProductCategoryId());
        setProductType(product, productDTO.getProductTypeId());
        Integer statusId = productDTO.getStatusId() == null ? activeStatusId : productDTO.getStatusId();
        setStatus(product, statusId);

        save(product);
        return product;
    }

    @Override
    public void delete(Integer productId) {
        EProduct product = getById(productId, true);
        productDAO.delete(product);
    }

    @Override
    public Optional<EProduct> getById(Integer productId) {
        return productDAO.findById(productId);
    }

    @Override
    public EProduct getById(Integer productId, Boolean handleException) {
        Optional<EProduct> product = getById(productId);
        if (!product.isPresent() && handleException) {
            throw new NotFoundException("product with specified id not found", "productId");
        }
        return product.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EProduct> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EProduct> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EProduct>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EProduct> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EProduct product) {
        productDAO.save(product);
    }

    private void setClient(EProduct product, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            product.setClient(client);
        }
    }

    private void setProductBrand(EProduct product, Integer productBrandId) {
        if (productBrandId != null) {
            EProductBrand productBrand = sProductBrand.getById(productBrandId, true);
            product.setProductBrand(productBrand);
        }
    }

    private void setProductCategory(EProduct product, Integer productCategoryId) {
        if (productCategoryId != null) {
            EProductCategory productCategory = sProductCategory.getById(productCategoryId, true);
            product.setProductCategory(productCategory);
        }
    }

    private void setProductType(EProduct product, Integer productTypeId) {
        if (productTypeId != null) {
            EProductType productType = sProductType.getById(productTypeId, true);
            product.setProductType(productType);
        }
    }

    private void setStatus(EProduct product, Integer statusId) {
        if (statusId != null) {
            EStatus status = sStatus.getById(statusId, true);
            product.setStatus(status);
        }
    }

    @Override
    public EProduct update(Integer productId, ProductDTO productDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EProduct product = getById(productId, true);

        String[] fields = {"Name", "Image", "Description"};
        for (String field : fields) {
            Method getField = ProductDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(productDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EProduct.class.getMethod("set" + field, fieldValue.getClass()).invoke(product, fieldValue);
            }
        }

        product.setModifiedOn(LocalDateTime.now());
        setProductBrand(product, productDTO.getProductBrandId());
        setProductCategory(product, productDTO.getProductCategoryId());
        setProductType(product, productDTO.getProductTypeId());
        setStatus(product, productDTO.getStatusId());

        save(product);
        return product;
    }
    
}
