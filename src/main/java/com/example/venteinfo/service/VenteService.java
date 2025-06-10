package com.example.venteinfo.service;

import com.example.venteinfo.model.Vente;
import com.example.venteinfo.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenteService {

    @Autowired
    private VenteRepository venteRepository;

    // Créer une vente
    public Vente createVente(Vente vente) {
        return venteRepository.save(vente);
    }

    // Lire toutes les ventes
    public List<Vente> getAllVentes() {
        return venteRepository.findAll();
    }

    // Lire une vente par ID
    public Optional<Vente> getVenteById(Long id) {
        return venteRepository.findById(id);
    }

    // Mettre à jour une vente
    public Vente updateVente(Long id, Vente venteDetails) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));
        vente.setClient(venteDetails.getClient());
        vente.setProduit(venteDetails.getProduit());
        vente.setQuantite(venteDetails.getQuantite());
        vente.setMontantTotal(venteDetails.getMontantTotal());
        return venteRepository.save(vente);
    }

    // Supprimer une vente
    public void deleteVente(Long id) {
        venteRepository.deleteById(id);
    }

    // Recherche par nom de produit ou client
    public List<Vente> searchVentes(String keyword) {
        return venteRepository.findByProduitNomContainingIgnoreCaseOrClientNomContainingIgnoreCase(keyword, keyword);
    }

    // Tri par date
    public List<Vente> getVentesSortedByDate(String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            return venteRepository.findAllByOrderByDateVenteDesc();
        }
        return venteRepository.findAllByOrderByDateVenteAsc();
    }
}