package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<Activite> activites;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<Repas> repasConsommes;
}
