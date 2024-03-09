package com.metaway.petshopkeycloak.repositories;

import com.metaway.petshopkeycloak.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
