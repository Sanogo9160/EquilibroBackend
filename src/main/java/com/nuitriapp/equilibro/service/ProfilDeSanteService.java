package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.*;
import com.nuitriapp.equilibro.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfilDeSanteService {

    @Autowired
    private ProfilDeSanteRepository profilDeSanteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MaladieRepository maladieRepository;

    @Autowired
    private ObjectifSanteRepository objectifSanteRepository;

    @Autowired
    private AllergieRepository allergieRepository;

    @Autowired
    private PreferenceAlimentaireRepository preferenceAlimentaireRepository;

    @Autowired
    private EdamamService edamamService;

    @Autowired
    private UtilisateurService utilisateurService;

    public List<ProfilDeSante> obtenirTousLesProfils() {
        return profilDeSanteRepository.findAll();
    }

    public ProfilDeSante obtenirProfilParId(Long id) {
        return profilDeSanteRepository.findById(id).orElse(null);
    }

    public ProfilDeSante ajouterProfil(Long utilisateurId, ProfilDeSante profilDeSante) {
        return utilisateurRepository.findById(utilisateurId)
                .map(utilisateur -> {
                    profilDeSante.setUtilisateur(utilisateur);
                    enregistrerAssociations(profilDeSante);
                    ProfilDeSante nouveauProfil = profilDeSanteRepository.save(profilDeSante);
                    genererRecettesPourProfil(nouveauProfil);
                    return nouveauProfil;
                })
                .orElse(null);
    }

    @Transactional
    public ProfilDeSante mettreAJourProfil(Long id, ProfilDeSante profilDeSante) {
        ProfilDeSante existingProfil = profilDeSanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé"));

        // Mis à jour les maladies, objectifs, allergies et préférences alimentaires
        existingProfil.setMaladies(profilDeSante.getMaladies());
        existingProfil.setObjectifs(profilDeSante.getObjectifs());
        existingProfil.setAllergies(profilDeSante.getAllergies());
        existingProfil.setPreferencesAlimentaires(profilDeSante.getPreferencesAlimentaires());

        // Mis à jour l'utilisateur
        Utilisateur existingUtilisateur = utilisateurRepository.findById(profilDeSante.getUtilisateur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Mis à jour des champs de l'utilisateur
        existingUtilisateur.setNom(profilDeSante.getUtilisateur().getNom());
        // Mettez à jour d'autres champs si nécessaire

        // Sauvegarde de l'utilisateur
        utilisateurRepository.save(existingUtilisateur);

        // Sauvegarde du profil de santé mis à jour
        return profilDeSanteRepository.save(existingProfil);
    }

    public void supprimerProfil(Long id) {
        profilDeSanteRepository.findById(id).ifPresent(profilDeSanteRepository::delete);
    }

    private void genererRecettesPourProfil(ProfilDeSante profil) {
        String query = genererRequeteProfil(profil);
        // Logique pour générer des recettes (commentée pour l'instant)
        // String healthLabel = "";
        // String diet = "";
        // int calories = 0;
        // RecetteResponse recetteResponse = edamamService.fetchRecettes(query, healthLabel, diet, calories);
        // Enregistrer les recettes récupérées
        // recetteResponse.getRecettes().forEach(recetteService::saveRecette);
    }

    private void enregistrerAssociations(ProfilDeSante profilDeSante) {
        profilDeSante.setMaladies(sauvegarderEntities(profilDeSante.getMaladies(), maladieRepository));
        profilDeSante.setObjectifs(sauvegarderEntities(profilDeSante.getObjectifs(), objectifSanteRepository));
        profilDeSante.setAllergies(sauvegarderEntities(profilDeSante.getAllergies(), allergieRepository));
        profilDeSante.setPreferencesAlimentaires(sauvegarderEntities(profilDeSante.getPreferencesAlimentaires(), preferenceAlimentaireRepository));
    }

    public String genererRequeteProfil(ProfilDeSante profil) {
        String query = Stream.of(
                        profil.getMaladies().stream().map(Maladie::getNom),
                        profil.getAllergies().stream().map(Allergie::getNom),
                        profil.getObjectifs().stream().map(ObjectifSante::getNom),
                        profil.getPreferencesAlimentaires().stream().map(PreferenceAlimentaire::getNom)
                )
                .flatMap(s -> s)
                .collect(Collectors.joining(", "));
        return URLEncoder.encode(query, StandardCharsets.UTF_8);
    }

    private <T> Set<T> sauvegarderEntities(Set<T> entities, JpaRepository<T, Long> repository) {
        return entities.stream()
                .map(repository::save)
                .collect(Collectors.toSet());
    }

    public ProfilDeSante obtenirProfilParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .map(Utilisateur::getProfilDeSante)
                .orElse(null);
    }

    public Long getUtilisateurIdByEmail(String email) {
        return utilisateurService.rechercherParEmail(email)
                .map(Utilisateur::getId)
                .orElse(null);
    }
}