package com.example.venteinfo.repository;

import com.example.venteinfo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.venteinfo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Recherche par nom ou email (insensible à la casse)
    List<Client> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email);

    // Tri par nom
    List<Client> findAllByOrderByNomAsc();
    List<Client> findAllByOrderByNomDesc();
}