package com.mpgl2.gestion_bib.repository;

import com.mpgl2.gestion_bib.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre,Long> {
}
