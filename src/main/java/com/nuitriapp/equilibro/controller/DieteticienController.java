package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Dieteticien;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.repository.UtilisateurRepository;
import com.nuitriapp.equilibro.service.DieteticienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/dieteticiens")
public class DieteticienController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/liste")
    public List<Utilisateur> getAllDieteticiens() {
        // Retourne uniquement les utilisateurs avec le rôle de DIETETICIEN
        return utilisateurRepository.findByRoleNom("DIETETICIEN");
    }

}