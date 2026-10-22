package com.pharmacie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Produit / médicament de la pharmacie (TP1).
 * Relations :
 *   - ManyToOne vers Categorie et Fournisseur
 *   - OneToOne vers DetailProduit
 *   - ManyToMany vers Tag (table de jointure produit_tag)
 */
@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    private Double prix;

    @Column(name = "code_barre", length = 100)
    private String codeBarre;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @OneToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id")
    private DetailProduit detailProduit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_tag",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    public Produit() {
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DetailProduit getDetailProduit() {
        return detailProduit;
    }

    public void setDetailProduit(DetailProduit detailProduit) {
        this.detailProduit = detailProduit;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit produit)) return false;
        return Objects.equals(id, produit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
