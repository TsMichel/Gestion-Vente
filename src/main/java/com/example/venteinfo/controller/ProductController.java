package com.example.venteinfo.controller;

import com.example.venteinfo.model.Produit;
import com.example.venteinfo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return productService.createProduit(produit);
    }

    @GetMapping
    public List<Produit> getAllProduits() {
        return productService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return productService.getProduitById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        Produit updatedProduit = productService.updateProduit(id, produitDetails);
        return ResponseEntity.ok(updatedProduit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        productService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Produit> searchProduits(@RequestParam String keyword) {
        return productService.searchProduits(keyword);
    }

    @GetMapping("/sorted")
    public List<Produit> getProduitsSorted(@RequestParam(defaultValue = "asc") String direction) {
        return productService.getProduitsSortedByName(direction);
    }
}