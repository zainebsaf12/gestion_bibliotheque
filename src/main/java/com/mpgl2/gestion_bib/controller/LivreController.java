package com.mpgl2.gestion_bib.controller;

import com.mpgl2.gestion_bib.entity.Livre;
import com.mpgl2.gestion_bib.service.LivreServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {
    private final LivreServiceService livreService;

    public LivreController(LivreServiceService livreService) {
        this.livreService = livreService;
    }

    @PostMapping
    public ResponseEntity<Livre> saveLivre(@RequestBody Livre livre) {
        Livre savedLivre = livreService.saveLivre(livre);
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Livre> updateLivre(@RequestBody Livre livreMaj) {
        Livre updatedLivre = livreService.updateLivre(livreMaj);
        return new ResponseEntity<>(updatedLivre, HttpStatus.OK);
    }

    @DeleteMapping("/{livreId}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long livreId) {
        livreService.deleteLivre(livreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Livre>> getLivresDispo() {
        List<Livre> livresDisponibles = livreService.getLivresDispo();

        return new ResponseEntity<>(livresDisponibles, HttpStatus.OK);
    }
}
