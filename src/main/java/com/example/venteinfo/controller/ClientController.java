package com.example.venteinfo.controller;

import com.example.venteinfo.model.Client;
import com.example.venteinfo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Créer un client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    // Lire tous les clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // Lire un client par ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un client
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client updatedClient = clientService.updateClient(id, clientDetails);
        return ResponseEntity.ok(updatedClient);
    }

    // Supprimer un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // Recherche par mot-clé
    @GetMapping("/search")
    public List<Client> searchClients(@RequestParam String keyword) {
        return clientService.searchClients(keyword);
    }

    // Tri par nom
    @GetMapping("/sorted")
    public List<Client> getClientsSorted(@RequestParam(defaultValue = "asc") String direction) {
        return clientService.getClientsSortedByName(direction);
    }
}