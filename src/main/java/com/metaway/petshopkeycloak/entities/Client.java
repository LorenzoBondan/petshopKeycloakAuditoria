package com.metaway.petshopkeycloak.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_client")
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant registerDate;
    @Column(columnDefinition = "TEXT")
    private String imgUrl;
    @OneToOne(mappedBy = "client")
    private Address address;
    @OneToOne(mappedBy = "client")
    private Contact contact;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

    public Client(){}

    public Client(Long id, String name, String cpf, Instant registerDate, String imgUrl, Address address, Contact contact) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Pet> getPets() {
        return pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}