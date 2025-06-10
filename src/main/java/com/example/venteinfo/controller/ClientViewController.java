package com.example.venteinfo.controller;

import com.example.venteinfo.model.Client;
import com.example.venteinfo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientViewController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients";
    }

    @GetMapping("/search")
    public String searchClients(@RequestParam String keyword, Model model) {
        model.addAttribute("clients", clientService.searchClients(keyword));
        return "clients";
    }

    @GetMapping("/sorted")
    public String sortedClients(@RequestParam(defaultValue = "asc") String direction, Model model) {
        model.addAttribute("clients", clientService.getClientsSortedByName(direction));
        return "clients";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client) {
        clientService.createClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        Client client = clientService.getClientById(id).orElseThrow();
        model.addAttribute("client", client);
        return "client-form";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam Long id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
}