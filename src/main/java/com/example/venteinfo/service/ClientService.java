package com.example.venteinfo.service;

import com.example.venteinfo.model.Client;
import com.example.venteinfo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Créer un client
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    // Lire tous les clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Lire un client par ID
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // Mettre à jour un client
    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        client.setNom(clientDetails.getNom());
        client.setEmail(clientDetails.getEmail());
        client.setTelephone(clientDetails.getTelephone());
        return clientRepository.save(client);
    }

    // Supprimer un client
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // Recherche par nom ou email
    public List<Client> searchClients(String keyword) {
        return clientRepository.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    // Tri par nom (ascendant ou descendant)
    public List<Client> getClientsSortedByName(String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            return clientRepository.findAllByOrderByNomDesc();
        }
        return clientRepository.findAllByOrderByNomAsc();
    }
}