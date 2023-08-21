package com.kigen.retail_store.models.user;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_roles")
@Data
@NoArgsConstructor
public class EUserRole implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRolePK userRolePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @MapsId(value = "roleId")
    private ERole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "userId")
    private EUser user;

    public EUserRole(EUser user, ERole role) {
        setUser(user);
        setRole(role);
        setUserRolePK(new UserRolePK(user.getId(), role.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }
        EUserRole userRole = (EUserRole) o;
        return user.getId() == userRole.getUser().getId();
    }

    @Override
    public int hashCode() {
        int hash = 78;
        hash = 29 * hash + user.getId().intValue();
        return hash;
    }
}
