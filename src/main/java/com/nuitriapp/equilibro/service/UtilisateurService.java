package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.*;
import com.nuitriapp.equilibro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utilisateur> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur obtenirUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur mettreAJourUtilisateur(Long id, Utilisateur utilisateur) {
        Utilisateur existant = utilisateurRepository.findById(id).orElse(null);
        if (existant != null) {
            existant.setNom(utilisateur.getNom());
            existant.setEmail(utilisateur.getEmail());
            existant.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            existant.setTelephone(utilisateur.getTelephone());
            existant.setPoids(utilisateur.getPoids());
            existant.setTaille(utilisateur.getTaille());
            existant.setAge(utilisateur.getAge());
            existant.setSexe(utilisateur.getSexe());
            existant.setRole(utilisateur.getRole());
            existant.setImageUrl(utilisateur.getImageUrl());
            return utilisateurRepository.save(existant);
        }
        return null;
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // Méthode pour rechercher un utilisateur par email
    public Optional<Utilisateur> rechercherParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}
