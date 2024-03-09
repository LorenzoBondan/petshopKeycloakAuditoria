package com.metaway.petshopkeycloak.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class CustomUserUtil {

    @Value("${jwt.auth.converter.resource-id}")
    private String apiClient;

    public String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        return jwtPrincipal.getClaim("preferred_username"); // username
    }

    public Collection<String> getLoggedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        Map<String, Object> resourceAccess = jwtPrincipal.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey(apiClient)) {
            Map<String, Object> apiResource = (Map<String, Object>) resourceAccess.get(apiClient);
            if (apiResource.containsKey("roles")) {
                return (Collection<String>) apiResource.get("roles");
            }
        }
        return Collections.emptyList();
    }
}
