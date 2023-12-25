package com.mpgl2.gestion_bib.controller;

import com.mpgl2.gestion_bib.entity.Membre;
import com.mpgl2.gestion_bib.service.MembreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public ResponseEntity<List<Membre>> getAllMembres() {
        List<Membre> membres = membreService.getAllMembres();
        return new ResponseEntity<>(membres, HttpStatus.OK);
    }

    @GetMapping("/{membreId}")
    public ResponseEntity<Membre> getMembreById(@PathVariable Long membreId) {
        Membre membre = membreService.getMembreById(membreId);
        return new ResponseEntity<>(membre, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Membre> saveMembre(@RequestBody Membre membre) {
        Membre savedMembre = membreService.saveMembre(membre);
        return new ResponseEntity<>(savedMembre, HttpStatus.CREATED);
    }

    @PutMapping("/{membreId}")
    public ResponseEntity<Membre> updateMembre(@PathVariable Long membreId, @RequestBody Membre membreMaj) {
        Membre updatedMembre = membreService.updateMembre(membreId, membreMaj);
        return new ResponseEntity<>(updatedMembre, HttpStatus.OK);
    }

    @DeleteMapping("/{membreId}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long membreId) {
        membreService.deleteMembre(membreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}