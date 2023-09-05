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
import com.kigen.retail_store.dtos.product.ProductDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.product.EProduct;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.services.product.IProduct;

@RestController
@RequestMapping(path = "/product")
public class CProduct {
    
    @Autowired
    private IProduct sProduct;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        EUser user = sUserDetails.getActiveUserByContact();
        if (user.getClient() != null) {
            productDTO.setCientId(user.getClient().getId());
        }

        EProduct product = sProduct.create(productDTO);

        return ResponseEntity
            .created(new URI("/" + product.getId()))
            .body(new SuccessResponse(201, "successfully created product", new ProductDTO(product)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getProductsList(@RequestParam Map<String, Object> params) 
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

        List<String> allowedFields = new ArrayList<>(Arrays.asList("client.id", "client.clientCode", "createdOn", 
            "modifiedOn", "name", "productBrand.id", "productCategory.id", "productType.id", "status.id"));

        Page<EProduct> products = sProduct.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched products", products, 
                ProductDTO.class, EProduct.class));
    }

    @GetMapping(path = "/{productId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getProduct(@PathVariable Integer productId) {

        checkOwner(productId);

        EProduct product = sProduct.getById(productId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched product", new ProductDTO(product)));
    }

    @PatchMapping(path = "/{productId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateProduct(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        checkOwner(productId);

        EProduct product = sProduct.update(productId, productDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated product", new ProductDTO(product)));
    }

    @DeleteMapping(path = "/{productId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable Integer productId) {

        checkOwner(productId);

        sProduct.delete(productId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted product", ""));
    }

    private void checkOwner(Integer productId) {
        if (!sUserDetails.checkIsSystemAdmin() && !sProduct.checkIsOwner(productId)) {
            throw new InvalidInputException("Sorry client does not match requester", "clientId");
        }
    }
}
