package com.example.venteinfo.controller;

import com.example.venteinfo.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/home")
    public String showStatistiques(Model model) {
        StatistiqueService.Statistiques stats = statistiqueService.calculerStatistiques();
        model.addAttribute("stats", stats);
        return "home";
    }
}