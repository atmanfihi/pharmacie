package com.pharmacie.controller;

import com.pharmacie.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur espace CLIENT (TP2 - Sécurisation).
 * Consultation en lecture seule du catalogue des médicaments.
 * Accès : CLIENT (et tous les rôles internes - voir SecurityConfig).
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    private final ProduitRepository produitRepository;

    public ClientController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping({"", "/produits"})
    public String catalogue(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "client";
    }
}
