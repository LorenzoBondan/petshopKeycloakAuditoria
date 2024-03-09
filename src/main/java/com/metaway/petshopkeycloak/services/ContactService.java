package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.dto.ContactDTO;
import com.metaway.petshopkeycloak.entities.Contact;
import com.metaway.petshopkeycloak.repositories.ClientRepository;
import com.metaway.petshopkeycloak.repositories.ContactRepository;
import com.metaway.petshopkeycloak.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public ContactDTO update(Long id, ContactDTO dto){
        Contact entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact Id not found: " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ContactDTO(entity);
    }

    public void copyDtoToEntity(ContactDTO dto, Contact entity){
        entity.setTag(dto.getTag());
        entity.setType(dto.getType());
        entity.setContactValue(dto.getContactValue());
        entity.setClient(clientRepository.getReferenceById(dto.getClientId()));
    }
}
