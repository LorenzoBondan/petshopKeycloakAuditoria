package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.AddressDTO;
import com.metaway.petshopkeycloak.repositories.AddressRepository;
import com.metaway.petshopkeycloak.services.AddressService;
import com.metaway.petshopkeycloak.services.AuthService;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressRepository repository;

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN', 'ROLE_ROLE_CLIENT')") // ROLE_ADMIN tem que colocar mais um ROLE_ na frente
    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto){
        authService.validateSelfOrAdmin(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address id not found")).getClient().getCpf());
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
