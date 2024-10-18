package com.nuitriapp.equilibro.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
//@EqualsAndHashCode(callSuper = true)
public class Dieteticien extends Utilisateur {
    private String specialite;

}
