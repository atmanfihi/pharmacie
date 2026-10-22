package com.pharmacie.controller.rest;

import com.pharmacie.model.Tag;
import com.pharmacie.repository.TagRepository;
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
 * Contrôleur REST des tags (TP1).
 */
@RestController
@RequestMapping("/api/tags")
public class TagRestController {

    private final TagRepository tagRepository;

    public TagRestController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<Tag> list() {
        return tagRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        tag.setId(null);
        Tag saved = tagRepository.save(tag);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public Tag update(@PathVariable Long id, @RequestBody Tag tag) {
        Tag existant = tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable"));
        existant.setLibelle(tag.getLibelle());
        return tagRepository.save(existant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable");
        }
        tagRepository.deleteById(id);
    }
}
