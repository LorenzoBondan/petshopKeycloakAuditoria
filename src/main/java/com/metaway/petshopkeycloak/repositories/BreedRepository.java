package com.metaway.petshopkeycloak.repositories;

import com.metaway.petshopkeycloak.entities.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
