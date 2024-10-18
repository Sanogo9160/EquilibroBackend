package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.*;

import com.nuitriapp.equilibro.service.ProfilDeSanteService;
import com.nuitriapp.equilibro.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private ProfilDeSanteService profilDeSanteService;

    @GetMapping("/liste")
    public List<Utilisateur> obtenirTousLesUtilisateurs() {
        return utilisateurService.obtenirTousLesUtilisateurs();
    }

    @GetMapping("/obtenir/{id}")
    public ResponseEntity<Utilisateur> obtenirUtilisateurParId(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(id);
        return utilisateur != null ? new ResponseEntity<>(utilisateur, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter/admin")
    public ResponseEntity<Utilisateur> ajouterAdmin(@RequestBody Administrateur administrateur) {
        Utilisateur nouvelAdministrateur = utilisateurService.ajouterUtilisateur(administrateur);
        return new ResponseEntity<>(nouvelAdministrateur, HttpStatus.CREATED);
    }

    @PostMapping("/ajouter/dieteticien")
    public ResponseEntity<Utilisateur> ajouterDieteticien(@RequestBody Dieteticien dieteticien) {
        Utilisateur nouvelDieteticien = utilisateurService.ajouterUtilisateur(dieteticien);
        return new ResponseEntity<>(nouvelDieteticien, HttpStatus.CREATED);
    }

    @PostMapping("/ajouter/utilisateur-simple")
    public ResponseEntity<Utilisateur> ajouterUtilisateurSimple(@RequestBody UtilisateurSimple utilisateurSimple) {
        Utilisateur nouvelUtilisateurSimple = utilisateurService.ajouterUtilisateur(utilisateurSimple);
        return new ResponseEntity<>(nouvelUtilisateurSimple, HttpStatus.CREATED);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Utilisateur> mettreAJourUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur utilisateurMisAJour = utilisateurService.mettreAJourUtilisateur(id, utilisateur);
        return utilisateurMisAJour != null ? new ResponseEntity<>(utilisateurMisAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recherche-email")
    public ResponseEntity<Utilisateur> rechercherParEmail(@RequestParam String email) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.rechercherParEmail(email);
        return utilisateurOpt.map(utilisateur -> new ResponseEntity<>(utilisateur, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}


