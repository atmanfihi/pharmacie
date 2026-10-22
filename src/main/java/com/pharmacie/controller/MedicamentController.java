package com.pharmacie.controller;

import com.pharmacie.model.Produit;
import com.pharmacie.repository.CategorieRepository;
import com.pharmacie.repository.FournisseurRepository;
import com.pharmacie.repository.ProduitRepository;
import com.pharmacie.repository.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pharmacie.model.Categorie;
import com.pharmacie.model.Fournisseur;
import com.pharmacie.model.Tag;
import java.util.List;

/**
 * Contrôleur MVC de gestion des médicaments (TP1 - affichage Thymeleaf + TP2 - rôles).
 * Accès réservé aux rôles ADMIN et PHARMACIEN (voir SecurityConfig).
 */
@Controller
@RequestMapping("/medicaments")
public class MedicamentController {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final FournisseurRepository fournisseurRepository;
    private final TagRepository tagRepository;

    public MedicamentController(ProduitRepository produitRepository,
                                CategorieRepository categorieRepository,
                                FournisseurRepository fournisseurRepository,
                                TagRepository tagRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "medicaments";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : " + id));
        model.addAttribute("produit", produit);
        return "produit-detail";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Produit produit = produitRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : " + id));
            model.addAttribute("produit", produit);
        } else {
            model.addAttribute("produit", new Produit());
        }
        return "produit-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Produit produit,
                       @RequestParam(required = false) List<Long> tagIds,
                       RedirectAttributes redirectAttributes) {
        if (produit.getId() != null && produit.getId() > 0) {
            Produit existant = produitRepository.findById(produit.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : " + produit.getId()));
            existant.setNom(produit.getNom());
            existant.setDescription(produit.getDescription());
            existant.setPrix(produit.getPrix());
            existant.setCodeBarre(produit.getCodeBarre());
            existant.setDateExpiration(produit.getDateExpiration());
            if (produit.getCategorie() != null && produit.getCategorie().getId() != null) {
                existant.setCategorie(produit.getCategorie());
            }
            if (produit.getFournisseur() != null && produit.getFournisseur().getId() != null) {
                existant.setFournisseur(produit.getFournisseur());
            }
            if (produit.getDetailProduit() != null) {
                existant.setDetailProduit(produit.getDetailProduit());
            }
            existant.getTags().clear();
            if (tagIds != null) {
                tagIds.forEach(tagId -> tagRepository.findById(tagId)
                        .ifPresent(existant.getTags()::add));
            }
            produitRepository.save(existant);
        } else {
            produit.setId(null);
            if (produit.getCategorie() != null && produit.getCategorie().getId() != null) {
                produit.setCategorie(categorieRepository.findById(produit.getCategorie().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable")));
            }
            if (produit.getFournisseur() != null && produit.getFournisseur().getId() != null) {
                produit.setFournisseur(fournisseurRepository.findById(produit.getFournisseur().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Fournisseur introuvable")));
            }
            produit.getTags().clear();
            if (tagIds != null) {
                tagIds.forEach(tagId -> tagRepository.findById(tagId)
                        .ifPresent(produit.getTags()::add));
            }
            produitRepository.save(produit);
        }
        redirectAttributes.addFlashAttribute("message", "Produit enregistré avec succès.");
        return "redirect:/medicaments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produitRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Produit supprimé.");
        return "redirect:/medicaments";
    }

    @ModelAttribute("categories")
    public List<Categorie> categories() {
        return categorieRepository.findAll();
    }

    @ModelAttribute("fournisseurs")
    public List<Fournisseur> fournisseurs() {
        return fournisseurRepository.findAll();
    }

    @ModelAttribute("tags")
    public List<Tag> tags() {
        return tagRepository.findAll();
    }
}
