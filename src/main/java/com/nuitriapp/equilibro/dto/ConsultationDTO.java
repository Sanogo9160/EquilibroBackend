package com.nuitriapp.equilibro.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationDTO {
    private Long utilisateurId;
    private Long dieteticienId;
    private LocalDateTime dateConsultation;
}
