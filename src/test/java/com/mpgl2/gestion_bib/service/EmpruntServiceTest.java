package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.entity.Membre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.EmpruntRepository;
import com.mpgl2.gestion_bib.repository.LivreRepository;
import com.mpgl2.gestion_bib.repository.MembreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpruntServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;

    @Mock
    private LivreRepository livreRepository;

    @Mock
    private MembreRepository membreRepository;

    @InjectMocks
    private EmpruntService empruntService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmprunt_livreEtMembreExistants() {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(new Livre());
        emprunt.setMembre(new Membre());

        when(livreRepository.findById(emprunt.getLivre().getId())).thenReturn(Optional.of(emprunt.getLivre()));
        when(membreRepository.findById(emprunt.getMembre().getId())).thenReturn(Optional.of(emprunt.getMembre()));

        Emprunt savedEmprunt = empruntService.saveEmprunt(emprunt);

        assertNotNull(savedEmprunt);
        verify(empruntRepository, times(1)).save(emprunt);
    }

    @Test
    void saveEmprunt_livreNonExistant() {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(new Livre());
        emprunt.setMembre(new Membre());

        when(livreRepository.findById(emprunt.getLivre().getId())).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> empruntService.saveEmprunt(emprunt));
        assertEquals("Livre non trouvé", exception.getMessage());
        verify(empruntRepository, never()).save(emprunt);
    }

    @Test
    void saveEmprunt_membreNonExistant() {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(new Livre());
        emprunt.setMembre(new Membre());

        when(livreRepository.findById(emprunt.getLivre().getId())).thenReturn(Optional.of(emprunt.getLivre()));
        when(membreRepository.findById(emprunt.getMembre().getId())).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> empruntService.saveEmprunt(emprunt));
        assertEquals("Membre non trouvé", exception.getMessage());
        verify(empruntRepository, never()).save(emprunt);
    }

    @Test
    void marquerLivreCommeRendu_empruntNonTrouve() {
        Long empruntId = 1L;
        when(empruntRepository.findById(empruntId)).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> empruntService.marquerLivreCommeRendu(empruntId));
        assertEquals("Emprunt non trouvé", exception.getMessage());
        verify(empruntRepository, never()).save(any(Emprunt.class));
    }

    @Test
    void marquerLivreCommeRendu_empruntDejaRendu() {
        Long empruntId = 1L;
        Emprunt emprunt = new Emprunt();
        emprunt.setId(empruntId);
        emprunt.setDateRetourEffectuee(LocalDate.now());

        when(empruntRepository.findById(empruntId)).thenReturn(Optional.of(emprunt));

        RessourceException exception = assertThrows(RessourceException.class, () -> empruntService.marquerLivreCommeRendu(empruntId));
        assertEquals("Emprunt déjà rendu.", exception.getMessage());
        verify(empruntRepository, never()).save(any(Emprunt.class));
    }

    @Test
    void marquerLivreCommeRendu_empruntNonRendu() {
        Long empruntId = 1L;
        Emprunt emprunt = new Emprunt();
        emprunt.setId(empruntId);

        when(empruntRepository.findById(empruntId)).thenReturn(Optional.of(emprunt));

        empruntService.marquerLivreCommeRendu(empruntId);

        assertNotNull(emprunt.getDateRetourEffectuee());
        assertNull(emprunt.getLivre());
        verify(empruntRepository, times(1)).save(emprunt);
    }
}
