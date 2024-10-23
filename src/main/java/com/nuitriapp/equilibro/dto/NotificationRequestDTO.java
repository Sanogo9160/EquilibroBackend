package com.nuitriapp.equilibro.dto;

import lombok.Data;

@Data
public class NotificationRequestDTO {
    private Long utilisateurId;
    private String message;
}
