package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.dto.DisponibiliteDTO;
import com.nuitriapp.equilibro.service.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilites")
public class DisponibiliteController {

    @Autowired
    private DisponibiliteService disponibiliteService;

    @PostMapping("/ajouter")
    public String ajouterDisponibilite(@RequestBody DisponibiliteDTO disponibiliteDTO) {
        disponibiliteService.ajouterDisponibilite(disponibiliteDTO);
        return "Disponibilité ajoutée avec succès.";
    }

    @GetMapping("/dieteticien/{dieteticienId}")
    public List<DisponibiliteDTO> getDisponibilites(@PathVariable Long dieteticienId) {
        return disponibiliteService.getDisponibilitesByDieteticien(dieteticienId);
    }
}
