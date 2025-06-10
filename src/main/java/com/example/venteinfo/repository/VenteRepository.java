package com.example.venteinfo.repository;

import com.example.venteinfo.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    // Recherche par nom du produit ou nom du client
    List<Vente> findByProduitNomContainingIgnoreCaseOrClientNomContainingIgnoreCase(String produitNom, String clientNom);

    // Tri par date
    List<Vente> findAllByOrderByDateVenteAsc();
    List<Vente> findAllByOrderByDateVenteDesc();
}