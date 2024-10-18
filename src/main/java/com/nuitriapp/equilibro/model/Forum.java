package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Sujet> sujets;

    /*
    @ManyToOne
    private Utilisateur utilisateur;

     */

}
