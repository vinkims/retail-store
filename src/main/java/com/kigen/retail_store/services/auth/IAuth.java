package com.kigen.retail_store.services.auth;

import com.kigen.retail_store.dtos.auth.AuthDTO;
import com.kigen.retail_store.dtos.auth.SignoutDTO;
import com.kigen.retail_store.models.user.EUser;

public interface IAuth {
    
    String authenticateUser(AuthDTO authDTO);

    EUser getUser(Integer userId);

    Boolean signoutUser(SignoutDTO signoutDTO);
}
