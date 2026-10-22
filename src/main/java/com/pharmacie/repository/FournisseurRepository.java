package com.pharmacie.repository;

import com.pharmacie.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository des fournisseurs (TP1).
 */
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
