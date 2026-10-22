package com.pharmacie.repository;

import com.pharmacie.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository des produits/médicaments (TP1).
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByNomContainingIgnoreCase(String nom);

    List<Produit> findByCategorieNomIgnoreCase(String nom);
}
