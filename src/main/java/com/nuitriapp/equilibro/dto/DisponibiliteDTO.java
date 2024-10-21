package com.nuitriapp.equilibro.dto;

import com.nuitriapp.equilibro.model.Disponibilite;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisponibiliteDTO {

    private Long id;
    private Long dieteticienId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;


    // Méthode pour convertir de l'entité Disponibilite vers DisponibiliteDTO
    public static DisponibiliteDTO fromEntity(Disponibilite disponibilite) {
        DisponibiliteDTO dto = new DisponibiliteDTO();
        dto.setId(disponibilite.getId());
        dto.setDieteticienId(disponibilite.getDieteticien().getId());
        dto.setDateDebut(disponibilite.getDateDebut());
        dto.setDateFin(disponibilite.getDateFin());
        return dto;
    }

    // Méthode pour convertir de DisponibiliteDTO vers l'entité Disponibilite
    public Disponibilite toEntity() {
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(this.id);

        // disponibilite.setDieteticien(...); // Cette ligne dépend de votre logique
        disponibilite.setDateDebut(this.dateDebut);
        disponibilite.setDateFin(this.dateFin);
        return disponibilite;
    }
}
