package com.kigen.retail_store.models.user;

import java.io.Serializable;

import com.kigen.retail_store.models.address.EAddress;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_addresses")
@Data
@NoArgsConstructor
public class EUserAddress implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserAddressPK userAddressPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @MapsId(value = "addressId")
    private EAddress address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "userId")
    private EUser user;

    @Column(name = "is_default")
    private Boolean isDefault;

    public EUserAddress(EUser user, EAddress address) {
        setAddress(address);
        setUser(user);
        setUserAddressPK(new UserAddressPK(user.getId(), address.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }
        EUserAddress userAddress = (EUserAddress) o;
        return user.getId() == userAddress.getUser().getId();
    }

    @Override
    public int hashCode() {
        int hash = 78;
        hash = 29 * hash + user.getId().intValue();
        return hash;
    }
}
