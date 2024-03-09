package com.metaway.petshopkeycloak.dto;

import com.metaway.petshopkeycloak.entities.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Required field")
    private String name;
    @NotBlank(message = "Required field")
    private String cpf;
    @PastOrPresent(message = "Register date must be past or present")
    private Instant registerDate;
    private String imgUrl;
    private AddressDTO address;
    private ContactDTO contact;
    private final List<PetDTO> pets = new ArrayList<>();

    public ClientDTO(){}

    public ClientDTO(Client entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.registerDate = entity.getRegisterDate();
        this.imgUrl = entity.getImgUrl();
        this.address = new AddressDTO(entity.getAddress());
        this.contact = new ContactDTO(entity.getContact());

        entity.getPets().forEach(pet -> this.pets.add(new PetDTO(pet)));
    }

    public ClientDTO(Long id, String name, String cpf, Instant registerDate, String imgUrl, AddressDTO address, ContactDTO contact) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.registerDate = registerDate;
        this.imgUrl = imgUrl;
        this.address = address;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
