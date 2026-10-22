package com.pharmacie.repository;

import com.pharmacie.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository des catégories (TP1).
 */
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
