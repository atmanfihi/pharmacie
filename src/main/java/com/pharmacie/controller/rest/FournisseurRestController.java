package com.pharmacie.controller.rest;

import com.pharmacie.model.Fournisseur;
import com.pharmacie.repository.FournisseurRepository;
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
 * Contrôleur REST des fournisseurs (TP1).
 */
@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurRestController {

    private final FournisseurRepository fournisseurRepository;

    public FournisseurRestController(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @GetMapping
    public List<Fournisseur> list() {
        return fournisseurRepository.findAll();
    }

    @GetMapping("/{id}")
    public Fournisseur get(@PathVariable Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Fournisseur> create(@RequestBody Fournisseur fournisseur) {
        fournisseur.setId(null);
        Fournisseur saved = fournisseurRepository.save(fournisseur);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public Fournisseur update(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        Fournisseur existant = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable"));
        existant.setNom(fournisseur.getNom());
        existant.setContact(fournisseur.getContact());
        existant.setAdresse(fournisseur.getAdresse());
        return fournisseurRepository.save(existant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fournisseur introuvable");
        }
        fournisseurRepository.deleteById(id);
    }
}
