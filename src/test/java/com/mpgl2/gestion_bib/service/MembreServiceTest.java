package com.mpgl2.gestion_bib.service;

import com.mpgl2.gestion_bib.entity.Membre;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.repository.MembreRepository;
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

class MembreServiceTest {

    @Mock
    private MembreRepository membreRepository;

    @InjectMocks
    private MembreService membreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMembres() {
        List<Membre> membres = new ArrayList<>();
        membres.add(new Membre());
        membres.add(new Membre());

        when(membreRepository.findAll()).thenReturn(membres);

        List<Membre> allMembres = membreService.getAllMembres();

        assertEquals(2, allMembres.size());
        verify(membreRepository, times(1)).findAll();
    }

    @Test
    void getMembreById_existingMembre() {
        Long membreId = 1L;
        Membre membre = new Membre();
        membre.setId(membreId);

        when(membreRepository.findById(membreId)).thenReturn(Optional.of(membre));

        Membre retrievedMembre = membreService.getMembreById(membreId);

        assertNotNull(retrievedMembre);
        assertEquals(membreId, retrievedMembre.getId());
        verify(membreRepository, times(1)).findById(membreId);
    }

    @Test
    void getMembreById_nonExistingMembre() {
        Long membreId = 1L;

        when(membreRepository.findById(membreId)).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> membreService.getMembreById(membreId));
        assertEquals("Membre non trouvé avec l'ID : " + membreId, exception.getMessage());
        verify(membreRepository, times(1)).findById(membreId);
    }

    @Test
    void saveMembre() {
        Membre membre = new Membre();
        when(membreRepository.save(any(Membre.class))).thenReturn(membre);

        Membre savedMembre = membreService.saveMembre(membre);

        assertNotNull(savedMembre);
        verify(membreRepository, times(1)).save(membre);
    }

    @Test
    void deleteMembre_existingMembre() {
        Long membreId = 1L;
        when(membreRepository.existsById(membreId)).thenReturn(true);

        assertDoesNotThrow(() -> membreService.deleteMembre(membreId));
        verify(membreRepository, times(1)).deleteById(membreId);
    }

    @Test
    void deleteMembre_nonExistingMembre() {
        Long membreId = 1L;
        when(membreRepository.existsById(membreId)).thenReturn(false);

        RessourceException exception = assertThrows(RessourceException.class, () -> membreService.deleteMembre(membreId));
        assertEquals("Membre non trouvé avec l'ID : " + membreId, exception.getMessage());
        verify(membreRepository, never()).deleteById(membreId);
    }

    @Test
    void updateMembre_existingMembre() {
        Long membreId = 1L;
        Membre membreMaj = new Membre();
        Membre existingMembre = new Membre();
        existingMembre.setId(membreId);

        when(membreRepository.findById(membreId)).thenReturn(Optional.of(existingMembre));
        when(membreRepository.save(any(Membre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Membre updatedMembre = membreService.updateMembre(membreId, membreMaj);

        assertNotNull(updatedMembre);
        assertEquals(membreId, updatedMembre.getId());
        verify(membreRepository, times(1)).save(existingMembre);
    }

    @Test
    void updateMembre_nonExistingMembre() {
        Long membreId = 1L;
        Membre membreMaj = new Membre();

        when(membreRepository.findById(membreId)).thenReturn(Optional.empty());

        RessourceException exception = assertThrows(RessourceException.class, () -> membreService.updateMembre(membreId, membreMaj));
        assertEquals("Membre non trouvé avec l'ID : " + membreId, exception.getMessage());
        verify(membreRepository, never()).save(any(Membre.class));
    }
}

