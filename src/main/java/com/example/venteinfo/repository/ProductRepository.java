package com.example.venteinfo.repository;

import com.example.venteinfo.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Produit, Long> {
    // Recherche par nom
    List<Produit> findByNomContainingIgnoreCase(String nom);

    // Tri par nom
    List<Produit> findAllByOrderByNomAsc();
    List<Produit> findAllByOrderByNomDesc();
}