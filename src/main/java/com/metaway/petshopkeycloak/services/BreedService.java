package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.dto.BreedDTO;
import com.metaway.petshopkeycloak.entities.Breed;
import com.metaway.petshopkeycloak.entities.Pet;
import com.metaway.petshopkeycloak.repositories.BreedRepository;
import com.metaway.petshopkeycloak.repositories.PetRepository;
import com.metaway.petshopkeycloak.services.exceptions.DataBaseException;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BreedService {

    @Autowired
    private BreedRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<BreedDTO> findAllPaged(Pageable pageable){
        Page<Breed> list = repository.findAll(pageable);
        return list.map(BreedDTO::new);
    }

    @Transactional(readOnly = true)
    public BreedDTO findById(Long id){
        Breed entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Breed Id not found: " + id));
        return new BreedDTO(entity);
    }

    @Transactional
    public BreedDTO insert(BreedDTO dto){
        Breed entity = new Breed();
        copyDtoToEntity(dto, entity);
        setCreationInfo(entity);
        entity = repository.save(entity);
        return new BreedDTO(entity);
    }

    @Transactional
    public BreedDTO update(Long id, BreedDTO dto){
        Breed entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Breed Id not found: " + id));
        copyDtoToEntity(dto, entity);
        setLastUpdateInfo(entity);
        entity = repository.save(entity);
        return new BreedDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        try{
            setLastUpdateInfo(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Breed Id not found: " + id)));
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(BreedDTO dto, Breed entity){
        entity.setDescription(dto.getDescription());
        /*
        entity.getPets().clear();
        for(Long petId : dto.getPetsId()){
            Pet pet = petRepository.getReferenceById(petId);
            entity.getPets().add(pet);
        }
        */
    }

    private void setCreationInfo(Breed entity){
        entity.setCreatedBy(authService.authenticatedUsername());
        entity.setCreationDate(LocalDateTime.now());
        entity.setLastUpdatedBy(authService.authenticatedUsername());
        entity.setLastUpdatedDate(entity.getCreationDate());
    }

    private void setLastUpdateInfo(Breed entity){
        entity.setLastUpdatedBy(authService.authenticatedUsername());
        entity.setLastUpdatedDate(LocalDateTime.now());
    }
}
