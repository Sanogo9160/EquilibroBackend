package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motif;

    @ManyToOne
    @JoinColumn(name = "dieteticien_id", nullable = false)
    private Dieteticien dieteticien;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    private LocalDateTime dateConsultation;

    private boolean estConfirmee;

    public Consultation() {
        this.estConfirmee = false;  // Par défaut, la consultation n'est pas confirmée
    }


}
