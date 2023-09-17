package com.kigen.retail_store.dtos.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.product.EProductItem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ProductItemDTO {
    
    private Integer id;

    private ProductDTO product;

    private Integer productId;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private Integer quantity;

    private BigDecimal price;

    private String image;

    private StatusDTO status;

    private Integer statusId;

    public ProductItemDTO(EProductItem productItem) {
        setCreatedOn(productItem.getCreatedOn());
        setId(productItem.getId());
        setImage(productItem.getImage());
        setModifiedOn(productItem.getModifiedOn());
        setPrice(productItem.getPrice());
        if (productItem.getProduct() != null) {
            setProduct(new ProductDTO(productItem.getProduct()));
        }
        setQuantity(productItem.getQuantity());
        if (productItem.getStatus() != null) {
            setStatus(new StatusDTO(productItem.getStatus()));
        }
    }
}
