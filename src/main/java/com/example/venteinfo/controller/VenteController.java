package com.example.venteinfo.controller;

import com.example.venteinfo.model.Vente;
import com.example.venteinfo.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {

    @Autowired
    private VenteService venteService;

    @PostMapping
    public Vente createVente(@RequestBody Vente vente) {
        return venteService.createVente(vente);
    }

    @GetMapping
    public List<Vente> getAllVentes() {
        return venteService.getAllVentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id) {
        return venteService.getVenteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente venteDetails) {
        Vente updatedVente = venteService.updateVente(id, venteDetails);
        return ResponseEntity.ok(updatedVente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        venteService.deleteVente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Vente> searchVentes(@RequestParam String keyword) {
        return venteService.searchVentes(keyword);
    }

    @GetMapping("/sorted")
    public List<Vente> getVentesSorted(@RequestParam(defaultValue = "asc") String direction) {
        return venteService.getVentesSortedByDate(direction);
    }
}