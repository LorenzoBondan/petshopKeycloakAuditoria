package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.ContactDTO;
import com.metaway.petshopkeycloak.repositories.ContactRepository;
import com.metaway.petshopkeycloak.services.AuthService;
import com.metaway.petshopkeycloak.services.ContactService;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private ContactRepository repository;

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable Long id, @Valid @RequestBody ContactDTO dto){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact id not found")).getClient().getCpf());
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
