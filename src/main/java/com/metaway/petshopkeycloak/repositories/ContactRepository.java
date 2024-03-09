package com.metaway.petshopkeycloak.repositories;

import com.metaway.petshopkeycloak.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
