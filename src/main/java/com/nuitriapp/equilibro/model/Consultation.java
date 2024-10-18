package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data

public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateConsultation;
    private String commentaires;

    @ManyToOne
    private Dieteticien dieteticien; // Fait reference au user Dieteticien

    @ManyToOne
    private UtilisateurSimple utilisateur;


}
