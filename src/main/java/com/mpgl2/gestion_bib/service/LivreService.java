package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivreService {
    private final LivreRepository livreRepository;


    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }


    public Livre saveLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public void deleteLivre(Long livreId) {
        if (livreRepository.existsById(livreId)) {
            livreRepository.deleteById(livreId);
        } else {
            throw new RessourceException("Livre non trouvé avec l'ID : " + livreId);
        }
    }

    public Livre updateLivre(Livre livreMaj) {
        Livre livreExistant = livreRepository.findById(livreMaj.getId())
                .orElseThrow(() -> new RessourceException("Livre n'existe pas"));

        livreExistant.setTitre(livreMaj.getTitre());
        livreExistant.setAuteur(livreMaj.getAuteur());
        livreExistant.setAnneePublication(livreMaj.getAnneePublication());
        livreExistant.setIsbn(livreMaj.getIsbn());

        return livreRepository.save(livreExistant);
    }

    public List<Livre> getLivresDispo() {
        return livreRepository.findAll().stream()
                .filter(livre -> livre.getEmprunts().isEmpty())
                .collect(Collectors.toList());
    }


}
