package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreService livreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLivre() {
        Livre livre = new Livre();
        when(livreRepository.save(any(Livre.class))).thenReturn(livre);

        Livre savedLivre = livreService.saveLivre(livre);

        assertNotNull(savedLivre);
        verify(livreRepository, times(1)).save(livre);
    }

    @Test
    void deleteLivre_existingLivre() {
        Long livreId = 1L;
        when(livreRepository.existsById(livreId)).thenReturn(true);

        assertDoesNotThrow(() -> livreService.deleteLivre(livreId));
        verify(livreRepository, times(1)).deleteById(livreId);
    }

    @Test
    void deleteLivre_nonExistingLivre() {
        Long livreId = 1L;
        when(livreRepository.existsById(livreId)).thenReturn(false);

        RessourceException exception = assertThrows(RessourceException.class, () -> livreService.deleteLivre(livreId));
        assertEquals("Livre non trouvé avec l'ID : " + livreId, exception.getMessage());
        verify(livreRepository, never()).deleteById(livreId);
    }

    @Test
    void updateLivre_existingLivre() {
        Livre livreMaj = new Livre();
        Livre existingLivre = new Livre();
        existingLivre.setId(1L);

        // Simulation d'un livre existant avec le même ID
        when(livreRepository.findById(existingLivre.getId())).thenReturn(Optional.of(existingLivre));

        // Simulation du comportement de la mise à jour dans le repository
        when(livreRepository.save(any(Livre.class))).thenReturn(livreMaj);

        Livre updatedLivre = livreService.updateLivre(existingLivre);

        assertNotNull(updatedLivre);
        assertEquals(livreMaj.getId(), updatedLivre.getId());
        verify(livreRepository, times(1)).save(existingLivre);
    }


    @Test
    void updateLivre_nonExistingLivre() {
        Livre livreMaj = new Livre();
        livreMaj.setId(1L);

        when(livreRepository.findById(livreMaj.getId())).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> livreService.updateLivre(livreMaj));
        assertEquals("Livre n'existe pas", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    @Test
    void getLivresDispo() {
        Livre livre1 = new Livre();
        livre1.setEmprunts(new ArrayList<>());

        Livre livre2 = new Livre();
        livre2.setEmprunts(List.of(new Emprunt())); // Livre avec un emprunt, donc non disponible

        List<Livre> allLivres = List.of(livre1, livre2);

        when(livreRepository.findAll()).thenReturn(allLivres);

        List<Livre> livresDispo = livreService.getLivresDispo();

        assertEquals(1, livresDispo.size());
        assertEquals(livre1, livresDispo.get(0));
    }
}
