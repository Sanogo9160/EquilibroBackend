package com.nuitriapp.equilibro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApportsNutritionnelsDTO {
    @NotNull(message = "Les calories sont requises")
    @Min(value = 0, message = "Les calories doivent être positives")
    private Double calories;

    @NotNull(message = "Les protéines sont requises")
    @Min(value = 0, message = "Les protéines doivent être positives")
    private Double proteines;

    @NotNull(message = "Les glucides sont requis")
    @Min(value = 0, message = "Les glucides doivent être positifs")
    private Double glucides;

    @NotNull(message = "Les lipides sont requis")
    @Min(value = 0, message = "Les lipides doivent être positifs")
    private Double lipides;

    @NotNull(message = "Les fibres sont requises")
    @Min(value = 0, message = "Les fibres doivent être positives")
    private Double fibres;

    @NotNull(message = "Le sodium est requis")
    @Min(value = 0, message = "Le sodium doit être positif")
    private Double sodium;

    @NotNull(message = "L'ID de l'utilisateur est requis")
    private Long utilisateurId; // ID de l'utilisateur associé
}
