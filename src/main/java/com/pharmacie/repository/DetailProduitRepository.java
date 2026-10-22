package com.pharmacie.repository;

import com.pharmacie.model.DetailProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository des détails de produits (TP1).
 */
@Repository
public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long> {
}
