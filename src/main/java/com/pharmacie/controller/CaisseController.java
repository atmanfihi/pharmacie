package com.pharmacie.controller;

import com.pharmacie.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur CAISSE (TP2 - Sécurisation).
 * Enregistrement des ventes : réservé aux rôles ADMIN, PHARMACIEN et CAISSIER.
 */
@Controller
@RequestMapping("/caisse")
public class CaisseController {

    private final ProduitRepository produitRepository;

    public CaisseController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping({"", "/ventes"})
    public String caisse(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "caisse";
    }
}
