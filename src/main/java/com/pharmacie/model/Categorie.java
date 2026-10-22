package com.pharmacie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Catégorie d'un produit (ex: Antibiotiques, Antalgiques, Vitamines, Antiseptiques).
 */
@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie categorie)) return false;
        return Objects.equals(id, categorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
