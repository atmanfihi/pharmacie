package com.pharmacie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Détails d'un produit (relation OneToOne avec Produit).
 */
@Entity
@Table(name = "detailProduit")
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fabricant;

    @Column(name = "paysOrigine", length = 100)
    private String paysOrigine;

    @Column(length = 1000)
    private String composition;

    @Column(length = 100)
    private String dosage;

    @Column(length = 1000)
    private String indications;

    @OneToOne(mappedBy = "detailProduit")
    private Produit produit;

    public DetailProduit() {
    }

    public DetailProduit(String fabricant, String paysOrigine, String composition,
                         String dosage, String indications) {
        this.fabricant = fabricant;
        this.paysOrigine = paysOrigine;
        this.composition = composition;
        this.dosage = dosage;
        this.indications = indications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricant() {
        return fabricant;
    }

    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }

    public String getPaysOrigine() {
        return paysOrigine;
    }

    public void setPaysOrigine(String paysOrigine) {
        this.paysOrigine = paysOrigine;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailProduit that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
