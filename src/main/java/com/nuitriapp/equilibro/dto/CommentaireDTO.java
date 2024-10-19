package com.nuitriapp.equilibro.dto;

import lombok.Data;

@Data
public class CommentaireDTO {
    private Long id;
    private String contenu;
    private Long utilisateurId; // Id de l'auteur du commentaire
    private Long forumId; // Id du forum lié
}
