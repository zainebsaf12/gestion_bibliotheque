package com.mpgl2.gestion_bib.controller;


import com.mpgl2.gestion_bib.entity.Emprunt;
import com.mpgl2.gestion_bib.exception.RessourceException;
import com.mpgl2.gestion_bib.service.EmpruntService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;


    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @PostMapping
    public ResponseEntity<Emprunt> saveEmprunt(
            @RequestBody Emprunt emprunt) {
        empruntService.saveEmprunt(emprunt);
        return new ResponseEntity<>(emprunt, HttpStatus.CREATED);
    }

    @PutMapping("/marquerRendu/{empruntId}")
    public ResponseEntity<Void> marquerLivreCommeRendu(@PathVariable Long empruntId) {
        empruntService.marquerLivreCommeRendu(empruntId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}