package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.dto.AssistanceDTO;
import com.metaway.petshopkeycloak.dto.PetDTO;
import com.metaway.petshopkeycloak.entities.Assistance;
import com.metaway.petshopkeycloak.entities.Pet;
import com.metaway.petshopkeycloak.repositories.AssistanceRepository;
import com.metaway.petshopkeycloak.repositories.BreedRepository;
import com.metaway.petshopkeycloak.repositories.ClientRepository;
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

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private AssistanceRepository assistanceRepository;

    @Transactional(readOnly = true)
    public Page<PetDTO> findAllPaged(Pageable pageable){
        Page<Pet> list = repository.findAll(pageable);
        return list.map(PetDTO::new);
    }

    @Transactional(readOnly = true)
    public PetDTO findById(Long id){
        Pet entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new PetDTO(entity);
    }

    @Transactional
    public PetDTO insert(PetDTO dto){
        Pet entity = new Pet();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PetDTO(entity);
    }

    @Transactional
    public PetDTO update(Long id, PetDTO dto){
        Pet entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PetDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        try{
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    public void copyDtoToEntity(PetDTO dto, Pet entity){
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setClient(clientRepository.getReferenceById(dto.getClientId()));
        entity.setBreed(breedRepository.getReferenceById(dto.getBreedId()));

        entity.getAssistances().clear();
        for(AssistanceDTO assistanceDTO : dto.getAssistances()){
            Assistance assistance = assistanceRepository.getReferenceById(assistanceDTO.getId());
            entity.getAssistances().add(assistance);
        }
    }
}