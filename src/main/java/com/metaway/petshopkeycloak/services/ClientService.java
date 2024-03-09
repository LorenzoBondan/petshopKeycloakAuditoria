package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.dto.ClientDTO;
import com.metaway.petshopkeycloak.dto.PetDTO;
import com.metaway.petshopkeycloak.entities.Client;
import com.metaway.petshopkeycloak.entities.Pet;
import com.metaway.petshopkeycloak.repositories.AddressRepository;
import com.metaway.petshopkeycloak.repositories.ClientRepository;
import com.metaway.petshopkeycloak.repositories.ContactRepository;
import com.metaway.petshopkeycloak.repositories.PetRepository;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(Pageable pageable){
        Page<Client> list = repository.findAll(pageable);
        return list.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client Id not found: " + id));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client Id not found: " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    public void copyDtoToEntity(ClientDTO dto, Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setImgUrl(dto.getImgUrl());
        entity.setAddress(addressRepository.getReferenceById(dto.getAddress().getId()));
        entity.setContact(contactRepository.getReferenceById(dto.getContact().getId()));

        entity.getPets().clear();
        for(PetDTO petDTO : dto.getPets()){
            Pet pet = petRepository.getReferenceById(petDTO.getId());
            entity.getPets().add(pet);
        }
    }
}
