package com.nuitriapp.equilibro.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dieteticien_id", nullable = false)
    private Dieteticien dieteticien;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;


}
