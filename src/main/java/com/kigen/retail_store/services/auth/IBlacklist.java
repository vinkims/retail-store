package com.kigen.retail_store.services.auth;

import com.kigen.retail_store.models.user.EBlacklistToken;
import com.kigen.retail_store.models.user.EUser;

public interface IBlacklist {
    
    Boolean checkExistsByToken(Integer tokenHash);

    EBlacklistToken create(String token, EUser user);

    Integer getTokenHash(String token);

    void save(EBlacklistToken blacklistToken);
}
