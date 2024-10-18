package com.nuitriapp.equilibro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class IndicateurPhysique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double poids;
    private Double glycemie;
    private String pressionArterielle;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "profil_de_sante_id")
    @JsonBackReference
    private ProfilDeSante profilDeSante;


}