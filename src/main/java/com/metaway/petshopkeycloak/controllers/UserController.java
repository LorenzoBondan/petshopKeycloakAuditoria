package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.UserDTO;
import com.metaway.petshopkeycloak.services.AuthService;
import com.metaway.petshopkeycloak.services.UserService;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @GetMapping(value = "/{userName}")
    public ResponseEntity<List<UserRepresentation>> findById(@PathVariable("userName") String userName){
        authService.validateSelfOrAdmin(userName);
        List<UserRepresentation> user = service.findById(userName);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO dto){
        UserDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(dto.getUserName()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") String id, @RequestBody UserDTO dto){
        authService.validateSelfOrAdmin(id);
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @PutMapping(value = "/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable("id") String id, @RequestParam(value = "password", defaultValue = "") String password){
        authService.validateSelfOrAdmin(id);
        service.updatePassword(id, password);
        return ResponseEntity.ok("Password updated");
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable("id") String id){
        authService.validateSelfOrAdmin(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @GetMapping(value = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){ // habilitar Realm settings, Email
        authService.validateSelfOrAdmin(userId);
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @GetMapping(value = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){ // habilitar Realm settings, Email
        authService.validateSelfOrAdmin(userId);
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}
