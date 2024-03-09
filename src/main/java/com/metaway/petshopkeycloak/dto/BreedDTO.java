package com.metaway.petshopkeycloak.dto;

import com.metaway.petshopkeycloak.entities.Breed;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BreedDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Required field")
    private String description;
    private Boolean deleted;
    private String createdBy;
    private LocalDateTime creationDate;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedDate;

    private final List<Long> petsId = new ArrayList<>();

    public BreedDTO(){}

    public BreedDTO(Breed entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.deleted = entity.getDeleted();
        this.createdBy = entity.getCreatedBy();
        this.creationDate = entity.getCreationDate();
        this.lastUpdatedBy = entity.getLastUpdatedBy();
        this.lastUpdatedDate = entity.getLastUpdatedDate();

        //entity.getPets().forEach(pet -> this.petsId.add(pet.getId()));
    }

    public BreedDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public BreedDTO(Long id, String description, Boolean deleted, String createdBy, LocalDateTime creationDate, String lastUpdatedBy, LocalDateTime lastUpdatedDate) {
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

    public List<Long> getPetsId() {
        return petsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreedDTO breedDTO = (BreedDTO) o;
        return Objects.equals(id, breedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
