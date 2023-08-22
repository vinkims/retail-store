package com.kigen.retail_store.services.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.auth.AuthDTO;
import com.kigen.retail_store.dtos.auth.SignoutDTO;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.user.UserRoleDTO;
import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.user.EBlacklistToken;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserRole;
import com.kigen.retail_store.services.user.IUser;
import com.kigen.retail_store.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SAuth implements IAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IBlacklist sBlacklist;

    @Autowired
    private IUser sUser;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SUserDetails sUserDetails;

    private List<UserRoleDTO> userRoles;
    
    @Override
    public String authenticateUser(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDTO.getUserContact(), authDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            log.info("{}", ex.getLocalizedMessage());
            throw new InvalidInputException("invalid credentials provided", "username/password");
        }

        UserDetails userDetails = sUserDetails.loadUserByUsername(authDTO.getUserContact());

        EUser user = sUser.getByIdOrContactValue(authDTO.getUserContact()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        if (user.getUserRoles() != null || !user.getUserRoles().isEmpty()) {
            userRoles = new ArrayList<>();
            for (EUserRole userRole : user.getUserRoles()) {
                userRoles.add(new UserRoleDTO(userRole));
            }
            claims.put("userRoles", userRoles);
        }
        if (user.getClient() != null) {
            claims.put("client", new ClientDTO(user.getClient()));
        }

        final String token = jwtUtil.generateToken(userDetails, claims);

        return token;
    }

    @Override
    public EUser getUser(Integer userId) {
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()) {
            throw new InvalidInputException("user not found", "userId");
        }
        return user.get();
    }

    @Override
    public Boolean signoutUser(SignoutDTO signoutDTO) {

        EUser user = getUser(signoutDTO.getUserId());

        EBlacklistToken blacklistToken = sBlacklist.create(signoutDTO.getToken(), user);

        return blacklistToken != null;
    }
    
}
