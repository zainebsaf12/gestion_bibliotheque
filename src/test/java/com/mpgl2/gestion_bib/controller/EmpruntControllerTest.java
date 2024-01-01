package com.mpgl2.gestion_bib.controller;
import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.service.EmpruntService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class EmpruntControllerTest {

    @Test
    void testSaveEmprunt() {
        // Créez une instance du service avec Mockito pour simuler son comportement
        EmpruntService empruntService = Mockito.mock(EmpruntService.class);
        EmpruntController empruntController = new EmpruntController(empruntService);

        // Créez un emprunt à utiliser dans le test
        Emprunt emprunt = new Emprunt();

        // Configurez le comportement simulé du service
        when(empruntService.saveEmprunt(emprunt)).thenReturn(emprunt);

        // Appelez la méthode du contrôleur que vous voulez tester
        ResponseEntity<Emprunt> responseEntity = empruntController.saveEmprunt(emprunt);

        // Assurez-vous que la réponse correspond à ce que vous attendez
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(emprunt, responseEntity.getBody());
    }

    @Test
    void testMarquerLivreCommeRendu() {
        // Créez une instance du service avec Mockito pour simuler son comportement
        EmpruntService empruntService = Mockito.mock(EmpruntService.class);
        EmpruntController empruntController = new EmpruntController(empruntService);

        // Créez un ID d'emprunt fictif pour le test
        Long empruntId = 1L;

        // Configurez le comportement simulé du service
        Mockito.doNothing().when(empruntService).marquerLivreCommeRendu(anyLong());

        // Appelez la méthode du contrôleur que vous voulez tester
        ResponseEntity<Void> responseEntity = empruntController.marquerLivreCommeRendu(empruntId);

        // Assurez-vous que la réponse correspond à ce que vous attendez
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
