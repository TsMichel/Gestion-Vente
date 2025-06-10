package com.example.venteinfo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    private LocalDateTime dateEmission;
    private String numeroFacture;

    // Constructeurs
    public Facture() {
        this.dateEmission = LocalDateTime.now();
    }

    public Facture(Vente vente, String numeroFacture) {
        this.vente = vente;
        this.dateEmission = LocalDateTime.now();
        this.numeroFacture = numeroFacture;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public LocalDateTime getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDateTime dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }
}