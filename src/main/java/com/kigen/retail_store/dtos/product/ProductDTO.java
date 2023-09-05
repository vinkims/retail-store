package com.kigen.retail_store.dtos.product;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.product.EProduct;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ProductDTO {
    
    private Integer id;

    private String name;

    private String description;

    private ClientDTO client;

    private Integer cientId;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private String image;

    private ProductCategoryDTO productCategory;

    private Integer productCategoryId;

    private ProductTypeDTO productType;

    private Integer productTypeId;

    private ProductBrandDTO productBrand;

    private Integer productBrandId;

    private StatusDTO status;

    private Integer statusId;

    public ProductDTO(EProduct product) {
        if (product.getClient() != null) {
            setClient(new ClientDTO(product.getClient()));
        }
        setCreatedOn(product.getCreatedOn());
        setDescription(product.getDescription());
        setId(product.getId());
        setImage(product.getImage());
        setModifiedOn(product.getModifiedOn());
        setName(product.getName());
        if (product.getProductBrand() != null) {
            setProductBrand(new ProductBrandDTO(product.getProductBrand()));
        }
        if (product.getProductCategory() != null) {
            setProductCategory(new ProductCategoryDTO(product.getProductCategory()));
        }
        if (product.getProductType() != null) {
            setProductType(new ProductTypeDTO(product.getProductType()));
        }
        if (product.getStatus() != null) {
            setStatus(new StatusDTO(product.getStatus()));
        }
    }
}
