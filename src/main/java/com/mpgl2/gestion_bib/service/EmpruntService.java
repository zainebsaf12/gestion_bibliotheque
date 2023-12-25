package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.EmpruntRepository;
import com.mpgl2.gestion_bib.repository.LivreRepository;
import com.mpgl2.gestion_bib.repository.MembreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;

    public EmpruntService(EmpruntRepository empruntRepository, LivreRepository livreRepository, MembreRepository membreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
        this.membreRepository = membreRepository;
    }

    public Emprunt saveEmprunt(Emprunt emprunt) {
        livreRepository.findById(emprunt.getLivre().getId())
                .orElseThrow(() -> new RessourceException("Livre non trouvé  "));

        membreRepository.findById(emprunt.getMembre().getId())
                .orElseThrow(() -> new RessourceException("Membre non trouvé "));


        empruntRepository.save(emprunt);
        return emprunt;
    }

    public void marquerLivreCommeRendu(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RessourceException("Emprunt non trouvé "));

        if (emprunt.getDateRetourEffectuee() == null) {
            emprunt.setDateRetourEffectuee(LocalDate.now());
            emprunt.setLivre(null);
            empruntRepository.save(emprunt);
        } else {
            throw new RessourceException("Emprunt déjà rendu.");
        }
    }


}