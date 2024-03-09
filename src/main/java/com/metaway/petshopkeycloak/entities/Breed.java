package com.metaway.petshopkeycloak.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_breed")
@SQLDelete(sql = "UPDATE tb_breed SET deleted = TRUE WHERE id=?")
public class Breed implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Boolean deleted = Boolean.FALSE;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    //@OneToMany(mappedBy = "breed", fetch = FetchType.EAGER)
    //private Set<Pet> pets = new HashSet<>(); // List was duplicating pets from users with more than one role

    public Breed(){}

    public Breed(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Breed(Long id, String description, Boolean deleted, String createdBy, LocalDateTime creationDate, String lastUpdatedBy, LocalDateTime lastUpdatedDate) {
        this.id = id;
        this.description = description;
        this.deleted = deleted;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    //public Set<Pet> getPets() {
        //return pets;
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return Objects.equals(id, breed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
