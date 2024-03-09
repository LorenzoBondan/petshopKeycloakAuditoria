package com.metaway.petshopkeycloak.controllers;

import com.metaway.petshopkeycloak.dto.AssistanceDTO;
import com.metaway.petshopkeycloak.services.AssistanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/assistances")
public class AssistanceController {

    @Autowired
    private AssistanceService service;

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AssistanceDTO>> findAll() {
        List<AssistanceDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @GetMapping(value = "/pageable")
    public ResponseEntity<Page<AssistanceDTO>> findAllPageable(Pageable pageable){
        Page<AssistanceDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AssistanceDTO> findById(@PathVariable Long id){
        AssistanceDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AssistanceDTO> insert(@Valid @RequestBody AssistanceDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AssistanceDTO> update(@PathVariable Long id, @Valid @RequestBody AssistanceDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AssistanceDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
