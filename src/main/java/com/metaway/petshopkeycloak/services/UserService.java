package com.metaway.petshopkeycloak.services;

import com.metaway.petshopkeycloak.config.Credentials;
import com.metaway.petshopkeycloak.config.KeycloakConfig;
import com.metaway.petshopkeycloak.dto.UserDTO;
import com.metaway.petshopkeycloak.entities.Address;
import com.metaway.petshopkeycloak.entities.Client;
import com.metaway.petshopkeycloak.entities.Contact;
import com.metaway.petshopkeycloak.repositories.AddressRepository;
import com.metaway.petshopkeycloak.repositories.ClientRepository;
import com.metaway.petshopkeycloak.repositories.ContactRepository;
import com.metaway.petshopkeycloak.services.exceptions.DataBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional // só por causa do client criado
    public UserDTO insert(UserDTO userDTO){
        if(!clientRepository.existsByCpf(userDTO.getUserName())) { // no banco de dados dev, consultar o cpf do user do keycloak
            CredentialRepresentation credential = Credentials
                    .createPasswordCredentials(userDTO.getPassword());
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userDTO.getUserName());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setCredentials(Collections.singletonList(credential));
            user.setEnabled(true);

            UsersResource instance = getInstance();
            instance.create(user);

            // iria parar aqui
            // ------ mas nessa aplicação específica, adicionar lógica para criar um Client para esse usuário, com base em seu nome e cpf
            Client client = new Client();
            Address address = new Address();
            Contact contact = new Contact();

            client.setCpf(user.getUsername());
            client.setName((user.getFirstName() + " " + user.getLastName()).trim());
            client.setImgUrl(null);
            client.setRegisterDate(Instant.now());

            address.setClient(client);
            addressRepository.save(address);

            contact.setClient(client);
            contactRepository.save(contact);

            clientRepository.save(client);
            return userDTO;
        }
        else{
            throw new DataBaseException("Cpf already exists");
        }
    }

    public List<UserRepresentation> findById(String userName){
        UsersResource usersResource = getInstance();
        return usersResource.search(userName, true);
    }

    public UserDTO update(String userId, UserDTO userDTO){
        //CredentialRepresentation credential = Credentials
                //.createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        //user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        List<UserRepresentation> entity = findById(userId);
        usersResource.get(entity.get(0).getId()).update(user);
        return userDTO;
    }

    public void updatePassword(String userId, String password){ // daria pra deixar junto do update, mas quis fazer um método separado
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(password);
        UserRepresentation user = new UserRepresentation();
        user.setCredentials(Collections.singletonList(credential));
        UsersResource usersResource = getInstance();
        List<UserRepresentation> entity = findById(userId);
        usersResource.get(entity.get(0).getId()).update(user);
    }

    public void delete(String userId){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> entity = findById(userId);
        usersResource.get(entity.get(0).getId()).remove();
    }

    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> entity = findById(userId);
        usersResource.get(entity.get(0).getId()).sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> entity = findById(userId);
        usersResource.get(entity.get(0).getId()).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }
}
