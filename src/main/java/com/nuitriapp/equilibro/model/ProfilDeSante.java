package com.nuitriapp.equilibro.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
public class ProfilDeSante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profil_maladie",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "maladie_id")
    )
    private Set<Maladie> maladies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profil_objectif",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "objectif_id")
    )
    private Set<ObjectifSante> objectifs;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profil_allergie",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "allergie_id")
    )
    private Set<Allergie> allergies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profil_preference",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "preference_id")
    )
    private Set<PreferenceAlimentaire> preferencesAlimentaires;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utilisateur_id")
    @JsonManagedReference
    private Utilisateur utilisateur;

}




