package com.pharmacie.repository;

import com.pharmacie.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository des tags (TP1).
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
