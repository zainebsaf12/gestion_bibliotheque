package com.mpgl2.gestion_bib.controller;

import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.service.LivreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class LivreControllerTest {

    @Mock
    private LivreService livreService;

    @InjectMocks
    private LivreController livreController;

    public LivreControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLivre() {
        Livre livre = new Livre();
        when(livreService.saveLivre(livre)).thenReturn(livre);

        ResponseEntity<Livre> responseEntity = livreController.saveLivre(livre);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(livre, responseEntity.getBody());
        verify(livreService, times(1)).saveLivre(livre);
    }

    @Test
    void updateLivre() {
        Livre livreMaj = new Livre();
        when(livreService.updateLivre(livreMaj)).thenReturn(livreMaj);

        ResponseEntity<Livre> responseEntity = livreController.updateLivre(livreMaj);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(livreMaj, responseEntity.getBody());
        verify(livreService, times(1)).updateLivre(livreMaj);
    }

    @Test
    void deleteLivre() {
        Long livreId = 1L;

        ResponseEntity<Void> responseEntity = livreController.deleteLivre(livreId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(livreService, times(1)).deleteLivre(livreId);
    }

    @Test
    void getLivresDispo() {
        List<Livre> livresDisponibles = new ArrayList<>();
        when(livreService.getLivresDispo()).thenReturn(livresDisponibles);

        ResponseEntity<List<Livre>> responseEntity = livreController.getLivresDispo();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(livresDisponibles, responseEntity.getBody());
        verify(livreService, times(1)).getLivresDispo();
    }
}

