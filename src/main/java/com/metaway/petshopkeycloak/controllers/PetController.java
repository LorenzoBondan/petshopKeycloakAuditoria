package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.PetDTO;
import com.metaway.petshopkeycloak.repositories.PetRepository;
import com.metaway.petshopkeycloak.services.AuthService;
import com.metaway.petshopkeycloak.services.PetService;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/pets")
public class PetController {

    @Autowired
    private PetService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private PetRepository repository;

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<PetDTO>> findAllPaged(Pageable pageable){
        Page<PetDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Long id){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet id not found")).getClient().getCpf());
        PetDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PetDTO> insert(@Valid @RequestBody PetDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PetDTO> update(@PathVariable Long id, @Valid @RequestBody PetDTO dto){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet id not found")).getClient().getCpf());
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PetDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
