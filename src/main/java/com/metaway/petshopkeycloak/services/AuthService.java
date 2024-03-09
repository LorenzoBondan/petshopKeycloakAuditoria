package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.dto.UserDTO;
import com.metaway.petshopkeycloak.services.exceptions.ForbiddenException;
import com.metaway.petshopkeycloak.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {

    @Autowired
    private CustomUserUtil customUserUtil;

    public String authenticatedUsername(){
        try{
            return customUserUtil.getLoggedUsername();
        }catch (Exception e){
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(String username) {
        String me = authenticatedUsername();
        Collection<String> roles = getLoggedUserRoles();
        if (roles.contains("ROLE_ADMIN")) {
            return;
        }
        if(!me.equals(username)) {
            throw new ForbiddenException("Access denied. Should be self or admin");
        }
    }

    public Collection<String> getLoggedUserRoles(){
        return customUserUtil.getLoggedUserRoles();
    }

    public UserDTO getSelf(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        UserDTO self = new UserDTO();
        self.setUserName(jwtPrincipal.getClaim("preferred_username"));
        self.setFirstName(jwtPrincipal.getClaim("given_name"));
        self.setLastName(jwtPrincipal.getClaim("family_name"));
        self.setEmail(jwtPrincipal.getClaim("email"));
        self.setPassword(null);
        return self;
    }
}
