package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.config.JwtUtil;
import com.nuitriapp.equilibro.model.ProfilDeSante;
import com.nuitriapp.equilibro.service.ProfilDeSanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profils-de-sante")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfilDeSanteController {

    @Autowired
    private ProfilDeSanteService profilDeSanteService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/liste")
    public List<ProfilDeSante> obtenirTousLesProfils() {
        return profilDeSanteService.obtenirTousLesProfils();}

    @GetMapping("/{id}")
    public ResponseEntity<ProfilDeSante> obtenirProfilParId(@PathVariable Long id) {
    ProfilDeSante profilDeSante = profilDeSanteService.obtenirProfilParId(id);
    return profilDeSante != null ? new ResponseEntity<>(profilDeSante, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter/{utilisateurId}")
    public ResponseEntity<ProfilDeSante> ajouterProfil(@PathVariable Long utilisateurId, @RequestBody ProfilDeSante profilDeSante) {
        ProfilDeSante nouveauProfil = profilDeSanteService.ajouterProfil(utilisateurId, profilDeSante);
        return nouveauProfil != null ? new ResponseEntity<>(nouveauProfil, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<ProfilDeSante> ajouterProfil(@RequestHeader("Authorization") String authHeader, @RequestBody ProfilDeSante profilDeSante) {
        // Extraire l'email du token JWT
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        // Récupérer l'id de l'utilisateur à partir de l'email
        Long userId = profilDeSanteService.getUtilisateurIdByEmail(email);

        // Créer le nouveau profil de santé avec l'id de l'utilisateur
        ProfilDeSante nouveauProfil = profilDeSanteService.ajouterProfil(userId, profilDeSante);
        return nouveauProfil != null ? new ResponseEntity<>(nouveauProfil, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<ProfilDeSante> mettreAJourProfil(@PathVariable Long id, @RequestBody ProfilDeSante profilDeSante) {
    ProfilDeSante profilMisAJour = profilDeSanteService.mettreAJourProfil(id, profilDeSante);
    return profilMisAJour != null ? new ResponseEntity<>(profilMisAJour, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerProfil(@PathVariable Long id) {
        profilDeSanteService.supprimerProfil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mon-profil")
    public ResponseEntity<ProfilDeSante> obtenirMonProfil(@RequestHeader("Authorization") String authHeader) {
        // Extraire le JWT du header Authorization
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        // Récupérer le profil de l'utilisateur via l'email extrait
        ProfilDeSante profilDeSante = profilDeSanteService.obtenirProfilParEmail(email);

        if (profilDeSante != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(profilDeSante);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}