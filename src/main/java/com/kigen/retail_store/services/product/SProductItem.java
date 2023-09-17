package com.kigen.retail_store.services.product;

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
import com.kigen.retail_store.dtos.product.ProductItemDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.product.EProduct;
import com.kigen.retail_store.models.product.EProductItem;
import com.kigen.retail_store.models.status.EStatus;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.product.ProductItemDAO;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.status.IStatus;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SProductItem implements IProductItem {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IProduct sProduct;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUserDetails sUserDetails;

    @Autowired
    private ProductItemDAO productItemDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkIsOwner(Integer productItemId) {
        EProductItem productItem = getById(productItemId, true);
        EUser user = sUserDetails.getActiveUserByContact();
        return user.getClient() == productItem.getProduct().getClient();
    }

    @Override
    public EProductItem create(ProductItemDTO productItemDTO) {

        EProductItem productItem = new EProductItem();
        productItem.setCreatedOn(LocalDateTime.now());
        productItem.setImage(productItemDTO.getImage());
        productItem.setPrice(productItemDTO.getPrice());
        setProduct(productItem, productItemDTO.getProductId());
        productItem.setQuantity(productItemDTO.getQuantity());
        Integer statusId = productItemDTO.getStatusId() == null ? activeStatusId : productItemDTO.getStatusId();
        setStatus(productItem, statusId);

        save(productItem);
        return productItem;
    }

    @Override
    public void delete(Integer productItemId) {
        EProductItem productItem = getById(productItemId, true);
        productItemDAO.delete(productItem);
    }

    @Override
    public Optional<EProductItem> getById(Integer productItemId) {
        return productItemDAO.findById(productItemId);
    }

    @Override
    public EProductItem getById(Integer productItemId, Boolean handleException) {
        Optional<EProductItem> productItem = getById(productItemId);
        if (!productItem.isPresent() && handleException) {
            throw new NotFoundException("product item with specified id not found", "productItemId");
        }
        return productItem.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EProductItem> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EProductItem> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EProductItem>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EProductItem> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return productItemDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EProductItem productItem) {
        productItemDAO.save(productItem);
    }

    private void setProduct(EProductItem productItem, Integer productId) {
        if (productId != null) {
            EProduct product = sProduct.getById(productId, true);
            productItem.setProduct(product);
        }
    }

    private void setStatus(EProductItem productItem, Integer statusId) {
        if (statusId != null) {
            EStatus status = sStatus.getById(statusId, true);
            productItem.setStatus(status);
        }
    }

    @Override
    public EProductItem update(Integer productItemId, ProductItemDTO productItemDTO) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EProductItem productItem = getById(productItemId, true);

        String[] fields = {"Quantity", "Price", "Image"};
        for (String field : fields) {
            Method getField = ProductItemDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(productItemDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EProductItem.class.getMethod("set" + field, fieldValue.getClass()).invoke(productItem, fieldValue);
            }
        }

        productItem.setModifiedOn(LocalDateTime.now());
        setProduct(productItem, productItemDTO.getProductId());
        setStatus(productItem, productItemDTO.getStatusId());

        save(productItem);
        return productItem;
    }
    
}
