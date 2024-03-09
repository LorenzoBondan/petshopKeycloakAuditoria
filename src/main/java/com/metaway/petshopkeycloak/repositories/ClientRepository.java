package com.metaway.petshopkeycloak.repositories;

import com.metaway.petshopkeycloak.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByCpf(String cpf);
}
