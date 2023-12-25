package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Membre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.MembreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreService {
    private final MembreRepository membreRepository;

    public MembreService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }


    public List<Membre> getAllMembres() {
        return membreRepository.findAll();
    }

    public Membre getMembreById(Long membreId) {
        return membreRepository.findById(membreId)
                .orElseThrow(() -> new RessourceException("Membre non trouvé avec l'ID : " + membreId));
    }

    public Membre saveMembre(Membre membre) {
        return membreRepository.save(membre);
    }

    public void deleteMembre(Long membreId) {
        if (membreRepository.existsById(membreId)) {
            membreRepository.deleteById(membreId);
        } else {
            throw new RessourceException("Membre non trouvé avec l'ID : " + membreId);
        }
    }

    public Membre updateMembre(Long membreId, Membre membreMaj) {
        Membre membreExistant = membreRepository.findById(membreId)
                .orElseThrow(() -> new RessourceException("Membre non trouvé avec l'ID : " + membreId));

        // Mettez à jour les champs nécessaires
        membreExistant.setNom(membreMaj.getNom());
        membreExistant.setPrenom(membreMaj.getPrenom());
        membreExistant.setAdresse(membreMaj.getAdresse());

        // Enregistrez la mise à jour dans la base de données
        return membreRepository.save(membreExistant);
    }
}
