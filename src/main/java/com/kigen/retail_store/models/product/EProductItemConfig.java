package com.kigen.retail_store.models.product;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "product_item_configs")
@Data
@NoArgsConstructor
public class EProductItemConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductItemConfigPK productItemConfigPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_type_id", referencedColumnName = "id")
    @MapsId(value = "configTypeId")
    private EConfigType configType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", referencedColumnName = "id")
    @MapsId(value = "productItemId")
    private EProductItem productItem;

    @Column(name = "value")
    private String value;

    public EProductItemConfig(EProductItem productItem, EConfigType configType) {
        setConfigType(configType);
        setProductItem(productItem);
        setProductItemConfigPK(new ProductItemConfigPK(productItem.getId(), configType.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }
        EProductItemConfig productItemConfig = (EProductItemConfig) o;
        return productItem.getId() == productItemConfig.getProductItem().getId();
    }

    @Override
    public int hashCode() {
        int hash = 65;
        hash = 31 * hash + productItem.getId().intValue();
        return hash;
    }
}
