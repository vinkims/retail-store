package com.kigen.retail_store.services.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kigen.retail_store.models.user.EUser;

public interface IUserDetails extends UserDetailsService {
    
    Boolean checkIsClientAdmin()
    
    Boolean checkIsSystemAdmin();

    EUser getActiveUserByContact();
}
