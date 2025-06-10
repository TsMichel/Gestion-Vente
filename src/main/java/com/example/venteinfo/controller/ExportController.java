package com.example.venteinfo.controller;

import com.example.venteinfo.model.Client;
import com.example.venteinfo.model.Produit;
import com.example.venteinfo.model.Vente;
import com.example.venteinfo.repository.ClientRepository;
import com.example.venteinfo.repository.ProductRepository;
import com.example.venteinfo.repository.VenteRepository;
import com.example.venteinfo.service.ExportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
public class ExportController {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final VenteRepository venteRepository;
    private final ExportService exportService;

    public ExportController(ClientRepository clientRepository, ProductRepository productRepository,
                            VenteRepository venteRepository, ExportService exportService) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.venteRepository = venteRepository;
        this.exportService = exportService;
    }

    @GetMapping("/export")
    public String showExportPage(Model model) {
        model.addAttribute("ventes", venteRepository.findAll()); // Ajouter la liste des ventes pour sélection
        return "export";
    }

    @PostMapping("/export")
    public ResponseEntity<InputStreamResource> exportData(
            @RequestParam("tables") List<String> tables) {

        List<Client> clients = tables.contains("clients") ? clientRepository.findAll() : null;
        List<Produit> produits = tables.contains("produits") ? productRepository.findAll() : null;
        List<Vente> ventes = tables.contains("ventes") ? venteRepository.findAll() : null;

        ByteArrayInputStream bis = exportService.exportToExcel(clients, produits, ventes);
        String filename = "data_export.xlsx";
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .body(new InputStreamResource(bis));
    }

    @PostMapping("/generate-facture")
    public ResponseEntity<InputStreamResource> generateFacture(
            @RequestParam("venteId") Long venteId) {

        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'ID : " + venteId));

        ByteArrayInputStream bis = exportService.generateFacture(vente);
        String filename = "facture_" + venteId + ".xlsx";
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .body(new InputStreamResource(bis));
    }
}