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
import com.kigen.retail_store.dtos.product.ProductTypeDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EProductType;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IProductType;

@RestController
@RequestMapping(path = "/product/type")
public class CProductType {

    @Autowired
    private IProductType sProductType;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProductType(@RequestBody ProductTypeDTO productTypeDTO) throws URISyntaxException {

        EProductType productType = sProductType.create(productTypeDTO);

        return ResponseEntity
            .created(new URI("/" + productType.getId()))
            .body(new SuccessResponse(200, "successfully created product type", new ProductTypeDTO(productType)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getProductTypesList(@RequestParam Map<String, Object> params) 
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

        List<String> allowedFields = new ArrayList<>(Arrays.asList("createdOn", "client.id", "client.clientCode", "name"));

        Page<EProductType> productTypes = sProductType.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched product types", productTypes, 
                ProductTypeDTO.class, EProductType.class));
    }

    @GetMapping(path = "/{productTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProductType(@PathVariable Integer productTypeId) {

        checkOwner(productTypeId);

        EProductType productType = sProductType.getById(productTypeId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched product type", new ProductTypeDTO(productType)));
    }

    @PatchMapping(path = "/{productTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateProductType(@PathVariable Integer productTypeId, @RequestBody ProductTypeDTO productTypeDTO) {

        checkOwner(productTypeId);

        EProductType productType = sProductType.update(productTypeId, productTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated product type", new ProductTypeDTO(productType)));
    }

    @DeleteMapping(path = "/{productTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteProductType(@PathVariable Integer productTypeId) {

        checkOwner(productTypeId);
        
        sProductType.delete(productTypeId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted product type", ""));
    }

    private void checkOwner(Integer productTypeId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sProductType.checkIsOwner(productTypeId)) {
            throw new InvalidInputException("Sorry client does not match requester", "productCategoryId");
        }
    }
    
}
