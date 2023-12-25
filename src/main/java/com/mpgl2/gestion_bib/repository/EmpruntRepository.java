package com.mpgl2.gestion_bib.repository;

import com.mpgl2.gestion_bib.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
}
