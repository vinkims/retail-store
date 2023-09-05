package com.kigen.retail_store.controllers.product;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.product.ProductCategoryDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EProductCategory;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IProductCategory;

@RestController
@RequestMapping(path = "/product/category")
public class CProductCategory {
    
    @Autowired
    private IProductCategory sProductCategory;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProductCategory(@RequestBody ProductCategoryDTO categoryDTO) throws URISyntaxException {

        EUser user = sUserDetails.getActiveUserByContact();
        if (user.getClient() != null) {
            categoryDTO.setClientId(user.getClient().getId());
        }

        EProductCategory productCategory = sProductCategory.create(categoryDTO);

        return ResponseEntity
            .created(new URI("/" + productCategory.getId()))
            .body(new SuccessResponse(201, "successfully created product category", new ProductCategoryDTO(productCategory)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getProductCategoriesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        if (!sUserDetails.checkIsSystemAdmin()) {
            EUser user = sUserDetails.getActiveUserByContact();
            if (user.getClient() != null) {
                String searchQuery = String.format(",client.idEQ%s", user.getClient().getId());
                pageDTO.setSearch(pageDTO.getSearch() + searchQuery);
            }
        }

        List<String> allowedFields = new ArrayList<>(Arrays.asList("client.id", "client.name", "client.clientCode", "createdOn", 
            "name", "parentCategory.id"));

        Page<EProductCategory> categories = sProductCategory.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched product categories", categories, 
                ProductCategoryDTO.class, EProductCategory.class));
    }

    @GetMapping(path = "/{categoryId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProductCategory(@PathVariable Integer categoryId) {

        checkOwner(categoryId);

        EProductCategory productCategory = sProductCategory.getById(categoryId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched product category", new ProductCategoryDTO(productCategory)));
    }

    @PatchMapping(path = "/{categoryId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateProductCategory(@PathVariable Integer categoryId, @RequestBody ProductCategoryDTO categoryDTO) {

        checkOwner(categoryId);

        EProductCategory productCategory = sProductCategory.update(categoryId, categoryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated product category", new ProductCategoryDTO(productCategory)));
    }

    @DeleteMapping(path = "/{categoryId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteProductCategory(@PathVariable Integer categoryId) {

        sProductCategory.delete(categoryId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted product category", ""));
    }

    private void checkOwner(Integer categoryId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sProductCategory.checkIsOwner(categoryId)) {
            throw new InvalidInputException("Sorry client does not match requester", "productCategoryId");
        }
    }
}
