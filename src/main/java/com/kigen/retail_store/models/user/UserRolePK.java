package com.kigen.retail_store.models.user;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class UserRolePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    public UserRolePK(Integer userId, Integer roleId) {
        setRoleId(roleId);
        setUserId(userId);
    }
}
