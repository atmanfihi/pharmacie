package com.pharmacie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur des pages générales : accueil et accès refusé (TP2 - Sécurisation).
 */
@Controller
public class PharmacieController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
