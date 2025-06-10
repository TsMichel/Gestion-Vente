package com.example.venteinfo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ventes")
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    private int quantite;
    private double montantTotal;
    private LocalDateTime dateVente;

    // Constructeurs
    public Vente() {
        this.dateVente = LocalDateTime.now();
    }

    public Vente(Client client, Produit produit, int quantite, double montantTotal) {
        this.client = client;
        this.produit = produit;
        this.quantite = quantite;
        this.montantTotal = montantTotal;
        this.dateVente = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }
}