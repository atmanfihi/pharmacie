package com.pharmacie.controller.rest;

import com.pharmacie.model.Produit;
import com.pharmacie.repository.ProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST des produits (TP1 - tests Postman).
 * Endpoints : GET /api/produits, GET /api/produits/{id},
 * POST /api/produits, PUT /api/produits/{id}, DELETE /api/produits/{id}
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {

    private final ProduitRepository produitRepository;

    public ProduitRestController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping
    public List<Produit> list() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produit get(@PathVariable Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produit> create(@RequestBody Produit produit) {
        produit.setId(null);
        Produit saved = produitRepository.save(produit);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public Produit update(@PathVariable Long id, @RequestBody Produit produit) {
        Produit existant = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable"));
        existant.setNom(produit.getNom());
        existant.setDescription(produit.getDescription());
        existant.setPrix(produit.getPrix());
        existant.setCodeBarre(produit.getCodeBarre());
        existant.setDateExpiration(produit.getDateExpiration());
        existant.setCategorie(produit.getCategorie());
        existant.setFournisseur(produit.getFournisseur());
        existant.setDetailProduit(produit.getDetailProduit());
        if (produit.getTags() != null) {
            existant.setTags(produit.getTags());
        }
        return produitRepository.save(existant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable");
        }
        produitRepository.deleteById(id);
    }
}
