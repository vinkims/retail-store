package com.kigen.retail_store.services.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.models.user.EBlacklistToken;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.repositories.user.BlacklistTokenDAO;

@Service
public class SBlacklist implements IBlacklist {

    @Autowired
    private BlacklistTokenDAO blacklistTokenDAO;

    @Override
    public Boolean checkExistsByToken(Integer tokenHash) {
        return blacklistTokenDAO.existsByTokenHash(tokenHash);
    }

    @Override
    public EBlacklistToken create(String token, EUser user) {

        EBlacklistToken blacklistToken = new EBlacklistToken();
        blacklistToken.setCreatedOn(LocalDateTime.now());
        blacklistToken.setTokenHash(token.hashCode());
        blacklistToken.setUser(user);

        save(blacklistToken);
        return blacklistToken;
    }

    @Override
    public Integer getTokenHash(String token) {
        return token.hashCode();
    }

    @Override
    public void save(EBlacklistToken blacklistToken) {
        blacklistTokenDAO.save(blacklistToken);
    }
    
}
