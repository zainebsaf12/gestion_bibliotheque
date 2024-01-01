package com.mpgl2.gestion_bib.integration;

import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.service.LivreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LivreControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LivreService livreService;

    @Test
    void testSaveLivre() {
        Livre livre = new Livre();
        livre.setTitre("Livre test");

        ResponseEntity<Livre> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/livres", livre, Livre.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Livre test", responseEntity.getBody().getTitre());
    }


    @Test
    void testUpdateLivre() {
        // Créez un livre à mettre à jour
        Livre livre = new Livre();
        livre.setTitre("Livre à mettre à jour");
        livre = livreService.saveLivre(livre);

        // Modifiez le titre du livre
        livre.setTitre("Livre mis à jour");

        // Créez une requête avec le corps (body) de la mise à jour
        RequestEntity<Livre> requestEntity = RequestEntity
                .put(URI.create("http://localhost:" + port + "/livres"))
                .body(livre);

        ResponseEntity<Livre> responseEntity = restTemplate.exchange(
                requestEntity,
                Livre.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Livre mis à jour", responseEntity.getBody().getTitre());
    }


    @Test
    void testDeleteLivre() {
        // Créez un livre à supprimer
        Livre livre = new Livre();
        livre.setTitre("Livre à supprimer");
        livre = livreService.saveLivre(livre);

        restTemplate.delete("http://localhost:" + port + "/livres/{livreId}", livre.getId());

        // Assurez-vous que le livre a été supprimé
        assertEquals(0, livreService.getLivresDispo().size());
    }

    @Test
    void testGetLivresDispo() {
        // Créez quelques livres disponibles
        Livre livre1 = new Livre();
        livre1.setTitre("Livre dispo 1");
        livreService.saveLivre(livre1);

        Livre livre2 = new Livre();
        livre2.setTitre("Livre dispo 2");
        livreService.saveLivre(livre2);

        ResponseEntity<Livre[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/livres/disponibles",
                Livre[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3, responseEntity.getBody().length);
    }
}
