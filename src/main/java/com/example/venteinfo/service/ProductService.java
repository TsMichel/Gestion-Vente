package com.example.venteinfo.service;

import com.example.venteinfo.model.Produit;
import com.example.venteinfo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Créer un produit
    public Produit createProduit(Produit produit) {
        return productRepository.save(produit);
    }

    // Lire tous les produits
    public List<Produit> getAllProduits() {
        return productRepository.findAll();
    }

    // Lire un produit par ID
    public Optional<Produit> getProduitById(Long id) {
        return productRepository.findById(id);
    }

    // Mettre à jour un produit
    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit produit = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        produit.setNom(produitDetails.getNom());
        produit.setDescription(produitDetails.getDescription());
        produit.setPrix(produitDetails.getPrix());
        produit.setStock(produitDetails.getStock());
        return productRepository.save(produit);
    }

    // Supprimer un produit
    public void deleteProduit(Long id) {
        productRepository.deleteById(id);
    }

    // Recherche par nom
    public List<Produit> searchProduits(String keyword) {
        return productRepository.findByNomContainingIgnoreCase(keyword);
    }

    // Tri par nom
    public List<Produit> getProduitsSortedByName(String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            return productRepository.findAllByOrderByNomDesc();
        }
        return productRepository.findAllByOrderByNomAsc();
    }
}