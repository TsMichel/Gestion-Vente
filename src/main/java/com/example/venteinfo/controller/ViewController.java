package com.example.venteinfo.controller;

import com.example.venteinfo.model.Produit;
import com.example.venteinfo.model.Vente;
import com.example.venteinfo.service.ClientService;
import com.example.venteinfo.service.ProductService;
import com.example.venteinfo.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VenteService venteService;

    @Autowired
    private ClientService clientService;

    // Produits
    @GetMapping("/produits")
    public String listProduits(Model model) {
        model.addAttribute("produits", productService.getAllProduits());
        return "produits";
    }

    @GetMapping("/produits/search")
    public String searchProduits(@RequestParam String keyword, Model model) {
        model.addAttribute("produits", productService.searchProduits(keyword));
        return "produits";
    }

    @GetMapping("/produits/sorted")
    public String sortedProduits(@RequestParam(defaultValue = "asc") String direction, Model model) {
        model.addAttribute("produits", productService.getProduitsSortedByName(direction));
        return "produits";
    }

    @GetMapping("/produits/add")
    public String showAddProduitForm(Model model) {
        model.addAttribute("produit", new Produit());
        return "produit-form";
    }

    @PostMapping("/produits/save")
    public String saveProduit(@ModelAttribute Produit produit) {
        productService.createProduit(produit);
        return "redirect:/produits";
    }

    @GetMapping("/produits/edit")
    public String showEditProduitForm(@RequestParam Long id, Model model) {
        Produit produit = productService.getProduitById(id).orElseThrow();
        model.addAttribute("produit", produit);
        return "produit-form";
    }

    @GetMapping("/produits/delete")
    public String deleteProduit(@RequestParam Long id) {
        productService.deleteProduit(id);
        return "redirect:/produits";
    }

    // Ventes
    @GetMapping("/ventes")
    public String listVentes(Model model) {
        model.addAttribute("ventes", venteService.getAllVentes());
        return "ventes";
    }

    @GetMapping("/ventes/search")
    public String searchVentes(@RequestParam String keyword, Model model) {
        model.addAttribute("ventes", venteService.searchVentes(keyword));
        return "ventes";
    }

    @GetMapping("/ventes/sorted")
    public String sortedVentes(@RequestParam(defaultValue = "asc") String direction, Model model) {
        model.addAttribute("ventes", venteService.getVentesSortedByDate(direction));
        return "ventes";
    }

    @GetMapping("/ventes/add")
    public String showAddVenteForm(Model model) {
        model.addAttribute("vente", new Vente());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("produits", productService.getAllProduits());
        return "vente-form";
    }

    @PostMapping("/ventes/save")
    public String saveVente(@ModelAttribute Vente vente) {
        venteService.createVente(vente);
        return "redirect:/ventes";
    }

    @GetMapping("/ventes/edit")
    public String showEditVenteForm(@RequestParam Long id, Model model) {
        Vente vente = venteService.getVenteById(id).orElseThrow();
        model.addAttribute("vente", vente);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("produits", productService.getAllProduits());
        return "vente-form";
    }

    @GetMapping("/ventes/delete")
    public String deleteVente(@RequestParam Long id) {
        venteService.deleteVente(id);
        return "redirect:/ventes";
    }
}