package com.example.venteinfo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private double prix;
    private int stock;

    // Constructeurs
    public Produit() {
    }

    public Produit(String nom, String description, double prix, int stock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}