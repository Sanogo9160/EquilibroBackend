package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;

    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires;


    /*
    @ManyToOne
    private Utilisateur utilisateur;

     */

}
