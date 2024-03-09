package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.BreedDTO;
import com.metaway.petshopkeycloak.services.BreedService;
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
@RequestMapping(value = "/breeds")
public class BreedController {

    @Autowired
    private BreedService service;

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BreedDTO>> findAllPaged(Pageable pageable){
        Page<BreedDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BreedDTO> findById(@PathVariable Long id){
        BreedDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BreedDTO> insert(@Valid @RequestBody BreedDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<BreedDTO> update(@PathVariable Long id, @Valid @RequestBody BreedDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BreedDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
