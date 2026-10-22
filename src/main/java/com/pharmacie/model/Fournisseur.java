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
 * Fournisseur d'un produit.
 */
@Entity
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 100)
    private String contact;

    @Column(length = 200)
    private String adresse;

    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<>();

    public Fournisseur() {
    }

    public Fournisseur(String nom, String contact, String adresse) {
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
        if (!(o instanceof Fournisseur fournisseur)) return false;
        return Objects.equals(id, fournisseur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
