package com.kigen.retail_store.models.product;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ProductItemConfigPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "config_type_id")
    private Integer configTypeId;

    @Column(name = "product_item_id")
    private Integer productItemId;

    public ProductItemConfigPK(Integer productItemId, Integer configTypeId) {
        setConfigTypeId(configTypeId);
        setProductItemId(productItemId);
    }
}
