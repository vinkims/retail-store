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
import com.kigen.retail_store.dtos.product.ProductBrandDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EProductBrand;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IProductBrand;

@RestController
@RequestMapping(path = "/product/brand")
public class CProductBrand {
    
    @Autowired
    private IProductBrand sProductBrand;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProductBrand(@RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {

        EUser user = sUserDetails.getActiveUserByContact();
        if (user.getClient() != null) {
            productBrandDTO.setClientId(user.getClient().getId());
        }

        EProductBrand productBrand = sProductBrand.create(productBrandDTO);

        return ResponseEntity
            .created(new URI("/" + productBrand.getId()))
            .body(new SuccessResponse(201, "successfully created product brand", new ProductBrandDTO(productBrand)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getProductBrandsList(@RequestParam Map<String, Object> params) 
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

        Page<EProductBrand> productBrands = sProductBrand.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched product brands", productBrands, 
                ProductBrandDTO.class, EProductBrand.class));
    }

    @GetMapping(path = "/{productBrandId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProductBrand(@PathVariable Integer productBrandId) {

        checkOwner(productBrandId);

        EProductBrand productBrand = sProductBrand.getById(productBrandId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched product brands", new ProductBrandDTO(productBrand)));
    }

    @PatchMapping(path = "/{productBrandId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateProductBrand(@PathVariable Integer productBrandId, @RequestBody ProductBrandDTO productBrandDTO) {

        checkOwner(productBrandId);

        EProductBrand productBrand = sProductBrand.update(productBrandId, productBrandDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated product brand", new ProductBrandDTO(productBrand)));
    }

    @DeleteMapping(path = "/{productBrandId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteProductBrand(@PathVariable Integer productBrandId) {

        checkOwner(productBrandId);

        sProductBrand.delete(productBrandId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted product brand", ""));
    }

    private void checkOwner(Integer productBrandId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sProductBrand.checkIsOwner(productBrandId)) {
            throw new InvalidInputException("Sorry client does not match requester", "productBrandId");
        }
    }
}
