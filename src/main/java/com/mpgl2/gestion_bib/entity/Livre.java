package com.mpgl2.gestion_bib.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private int anneePublication;
    private String isbn;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;
    public Livre(String titre, String auteur, int anneePublication, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
    }
}
