package com.metaway.petshopkeycloak.dto;

import com.metaway.petshopkeycloak.entities.Assistance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class AssistanceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Required field")
    private String description;
    @PositiveOrZero(message = "Assistance value must be positive or zero")
    private Double assistanceValue;
    private Instant date;
    @NotNull
    private Long petId;

    public AssistanceDTO(){}

    public AssistanceDTO(Assistance entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.assistanceValue = entity.getAssistanceValue();
        this.date = entity.getDate();
        this.petId = entity.getPet().getId();
    }

    public AssistanceDTO(Long id, String description, Double assistanceValue, Instant date, Long petId) {
        this.id = id;
        this.description = description;
        this.assistanceValue = assistanceValue;
        this.date = date;
        this.petId = petId;
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

    public Double getAssistanceValue() {
        return assistanceValue;
    }

    public void setAssistanceValue(Double assistanceValue) {
        this.assistanceValue = assistanceValue;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssistanceDTO that = (AssistanceDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
