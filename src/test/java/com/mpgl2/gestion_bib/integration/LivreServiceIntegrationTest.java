package com.mpgl2.gestion_bib.integration;

import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.LivreRepository;
import com.mpgl2.gestion_bib.service.LivreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class LivreServiceIntegrationTest {

    @Autowired
    private LivreService livreService;

    @Autowired
    private LivreRepository livreRepository;

    @Test
    void testSaveLivre() {
        Livre livre = new Livre();
        livre.setTitre("Livre test");
        Livre savedLivre = livreService.saveLivre(livre);

        assertEquals("Livre test", savedLivre.getTitre());
    }

    @Test
    void testUpdateLivre() {
        Livre livre = new Livre();
        livre.setTitre("Livre original");
        Livre savedLivre = livreRepository.save(livre);

        savedLivre.setTitre("Livre modifié");
        Livre updatedLivre = livreService.updateLivre(savedLivre);

        assertEquals("Livre modifié", updatedLivre.getTitre());
    }

    @Test
    void testDeleteLivre() {
        Livre livre = new Livre();
        livre.setTitre("Livre à supprimer");
        Livre savedLivre = livreRepository.save(livre);

        livreService.deleteLivre(savedLivre.getId());

        assertThrows(RessourceException.class, () -> {
            livreService.deleteLivre(savedLivre.getId());
        });    }

    @Test
    void testGetLivresDispo() {
        Livre livre1 = new Livre();
        livre1.setTitre("Livre disponible");
        livre1.setEmprunts(Collections.emptyList());
        livreRepository.save(livre1);

        Livre livre2 = new Livre();
        livre2.setTitre("Livre emprunté");
        livre2.setEmprunts(Collections.singletonList(new Emprunt()));
        livreRepository.save(livre2);

        List<Livre> livresDispo = livreService.getLivresDispo();

        assertEquals(1, livresDispo.size());
        assertEquals("Livre disponible", livresDispo.get(0).getTitre());
    }
}
