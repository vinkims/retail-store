package com.kigen.retail_store.controllers.product;

import java.lang.reflect.InvocationTargetException;
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
import com.kigen.retail_store.dtos.product.ProductItemDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EProductItem;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IProductItem;

@RestController
@RequestMapping(path = "/product/item")
public class CProductItem {
    
    @Autowired
    private IProductItem sProductItem;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProductItem(@RequestBody ProductItemDTO productItemDTO) {

        EProductItem productItem = sProductItem.create(productItemDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully created product item", new ProductItemDTO(productItem)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getProductItemsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (!sUserDetails.checkIsSystemAdmin()) {
            EUser user = sUserDetails.getActiveUserByContact();
            if (user.getClient() != null) {
                String searchQuery = String.format(",product.client.idEQ%s", user.getClient().getId());
                pageDTO.setSearch(pageDTO.getSearch() + searchQuery);
            }
        }

        List<String> allowedFields = new ArrayList<>(Arrays.asList("product.id", "product.client.id", "product.client.clientCode", 
            "createdOn", "modifiedOn", "quantity", "price", "status.id"));

        Page<EProductItem> productItems = sProductItem.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched product items", productItems, 
                ProductItemDTO.class, EProductItem.class));
    }

    @GetMapping(path = "/{productItemId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProductItem(@PathVariable Integer productItemId) {

        checkOwner(productItemId);

        EProductItem productItem = sProductItem.getById(productItemId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched product item", new ProductItemDTO(productItem)));
    }

    @PatchMapping(path = "/{productItemId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateProductItem(@PathVariable Integer productItemId, @RequestBody ProductItemDTO productItemDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        checkOwner(productItemId);

        EProductItem productItem = sProductItem.update(productItemId, productItemDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated product item", new ProductItemDTO(productItem)));
    }

    @DeleteMapping(path = "/{productItemId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteProductItem(@PathVariable Integer productItemId) {

        checkOwner(productItemId);

        sProductItem.delete(productItemId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted product item", ""));
    }

    private void checkOwner(Integer productItemId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sProductItem.checkIsOwner(productItemId)) {
            throw new InvalidInputException("Sorry client does not match requester", "clientId");
        }
    }
}
