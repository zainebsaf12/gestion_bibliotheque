package com.mpgl2.gestion_bib.repository;

import com.mpgl2.gestion_bib.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
}
