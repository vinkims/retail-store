package com.kigen.retail_store.services.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.exceptions.InvalidInputException;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.services.user.IUser;

@Service
public class SUserDetails implements IUserDetails {

    @Value(value = "${default.value.role.client-admin-id}")
    private Integer adminRoleId;

    @Value(value = "${default.value.role.system-admin-id}")
    private Integer systemAdminRoleId;

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

    @Override
    public Boolean checkIsClientAdmin() {
        EUser activeUser = getActiveUserByContact();
        List<Integer> userRoleIds = activeUser.getUserRoles()
            .stream()
            .map(uRole -> uRole.getRole().getId())
            .collect(Collectors.toList());
        return userRoleIds.contains(adminRoleId);
    }

    @Override
    public Boolean checkIsSystemAdmin() {
        EUser activeUser = getActiveUserByContact();
        List<Integer> userRoleIds = activeUser.getUserRoles()
            .stream()
            .map(uRole -> uRole.getRole().getId())
            .collect(Collectors.toList());
        return userRoleIds.contains(systemAdminRoleId);
    }

    @Override
    public EUser getActiveUserByContact() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<EUser> activeUser = sUser.getByContactValue(((UserDetails) principal).getUsername());
        return activeUser.get();
    }
    
}
