package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.ClientDTO;
import com.metaway.petshopkeycloak.repositories.ClientRepository;
import com.metaway.petshopkeycloak.services.AuthService;
import com.metaway.petshopkeycloak.services.ClientService;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllPaged(Pageable pageable){
        Page<ClientDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found")).getCpf());
        ClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client id not found")).getCpf());
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
