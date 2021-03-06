package com.example.AnnuaireBHCI.repository;

import com.example.AnnuaireBHCI.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByMatricule(String matricule);
}
