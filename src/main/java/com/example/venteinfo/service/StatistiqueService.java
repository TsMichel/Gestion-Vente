package com.example.venteinfo.service;

import com.example.venteinfo.model.Produit;
import com.example.venteinfo.model.Vente;
import com.example.venteinfo.repository.ClientRepository;
import com.example.venteinfo.repository.ProductRepository;
import com.example.venteinfo.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VenteRepository venteRepository;

    // Classe interne pour encapsuler les statistiques
    public static class Statistiques {
        private long totalProduits;
        private long totalClients;
        private long totalVentes;
        private BigDecimal chiffreAffaires;
        private Map<String, Long> produitPlusVendu; // Nom du produit et quantité vendue
        private long ventesDernierMois;
        private Map<String, Map<String, Long>> produitsParClient; // Nouveau champ

        // Constructeur
        public Statistiques(long totalProduits, long totalClients, long totalVentes,
                            BigDecimal chiffreAffaires, Map<String, Long> produitPlusVendu,
                            long ventesDernierMois, Map<String, Map<String, Long>> produitsParClient) {
            this.totalProduits = totalProduits;
            this.totalClients = totalClients;
            this.totalVentes = totalVentes;
            this.chiffreAffaires = chiffreAffaires;
            this.produitPlusVendu = produitPlusVendu;
            this.ventesDernierMois = ventesDernierMois;
            this.produitsParClient = produitsParClient;
        }

        // Getters
        public long getTotalProduits() {
            return totalProduits;
        }

        public long getTotalClients() {
            return totalClients;
        }

        public long getTotalVentes() {
            return totalVentes;
        }

        public BigDecimal getChiffreAffaires() {
            return chiffreAffaires;
        }

        public Map<String, Long> getProduitPlusVendu() {
            return produitPlusVendu;
        }

        public long getVentesDernierMois() {
            return ventesDernierMois;
        }

        public Map<String, Map<String, Long>> getProduitsParClient() {
            return produitsParClient;
        }
    }

    // Méthode pour calculer toutes les statistiques
    public Statistiques calculerStatistiques() {
        // Total produits
        long totalProduits = productRepository.count();

        // Total clients
        long totalClients = clientRepository.count();

        // Total ventes
        long totalVentes = venteRepository.count();

        // Chiffre d'affaires
        List<Vente> ventes = venteRepository.findAll();
        BigDecimal chiffreAffaires = BigDecimal.ZERO;
        for (Vente vente : ventes) {
            chiffreAffaires = chiffreAffaires.add(BigDecimal.valueOf(vente.getMontantTotal()));
        }

        // Produit le plus vendu
        Map<Long, Long> quantitesParProduit = ventes.stream()
                .collect(Collectors.groupingBy(
                        vente -> vente.getProduit().getId(),
                        Collectors.summingLong(Vente::getQuantite)
                ));

        Map.Entry<Long, Long> produitPlusVenduEntry = quantitesParProduit.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        Map<String, Long> produitPlusVendu = new HashMap<>();
        if (produitPlusVenduEntry != null) {
            Produit produit = productRepository.findById(produitPlusVenduEntry.getKey()).orElse(null);
            if (produit != null) {
                produitPlusVendu.put(produit.getNom(), produitPlusVenduEntry.getValue());
            }
        }

        // Ventes du dernier mois
        LocalDateTime unMoisAvant = LocalDateTime.now().minusMonths(1);
        long ventesDernierMois = ventes.stream()
                .filter(vente -> vente.getDateVente().isAfter(unMoisAvant))
                .count();

        // Calcul des produits par client
        Map<String, Map<String, Long>> produitsParClient = new HashMap<>();
        for (Vente vente : ventes) {
            String clientNom = vente.getClient().getNom();
            String produitNom = vente.getProduit().getNom();
            produitsParClient.computeIfAbsent(clientNom, k -> new HashMap<>())
                    .merge(produitNom, Long.valueOf(vente.getQuantite()), Long::sum);
        }

        return new Statistiques(totalProduits, totalClients, totalVentes, chiffreAffaires,
                produitPlusVendu, ventesDernierMois, produitsParClient);
    }
}