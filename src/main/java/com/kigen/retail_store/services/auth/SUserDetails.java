package com.kigen.retail_store.services.auth;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.services.user.IUser;

@Service
public class SUserDetails implements UserDetailsService {

    @Autowired
    private IUser sUser;

    @Override
    public UserDetails loadUserByUsername(String contactValue) throws UsernameNotFoundException {

        Optional<EUser> user = sUser.getByIdOrContactValue(contactValue);

        if (!user.isPresent()) {
            throw new InvalidInputException("invalid credentials provided", "user/password");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        String passcode = user.get().getPassword();
        UserDetails userDetails = (UserDetails) new User(contactValue, passcode == null ? "" : passcode, grantedAuthorities);

        return userDetails;
    }

    public EUser getActiveUserByContact() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<EUser> activeUser = sUser.getByContactValue(((UserDetails) principal).getUsername());
        return activeUser.get();
    }
    
}
