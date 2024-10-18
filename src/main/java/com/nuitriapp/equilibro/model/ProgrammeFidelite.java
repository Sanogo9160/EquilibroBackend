package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProgrammeFidelite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int points;
    private String niveau;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur; // Fait référence à l'utilisateur qui a ces points de fidélité

}
