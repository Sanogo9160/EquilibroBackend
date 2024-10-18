package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Dieteticien;
import com.nuitriapp.equilibro.service.DieteticienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("api/dieteticiens")
public class DieteticienController {
/*
    @Autowired
    private DieteticienService dieteticienService;

    @PostMapping("/creer")
    public Dieteticien creerDieteticien(@RequestBody Dieteticien dieteticien) {
        return dieteticienService.creerDieteticien(dieteticien);
    }

    @GetMapping("/{id}")
    public Dieteticien obtenirDieteticien(@PathVariable Long id) {
        return dieteticienService.obtenirDieteticienParId(id);
    }

    @PutMapping("modifier/{id}")
    public Dieteticien modifierDieteticien(@PathVariable Long id, @RequestBody Dieteticien dieteticien) {
        return dieteticienService.modifierDieteticien(id, dieteticien);
    }

    @DeleteMapping("supprimer/{id}")
    public void supprimerDieteticien(@PathVariable Long id) {
        dieteticienService.supprimerDieteticien(id);
    }

    @GetMapping("/liste")
    public List<Dieteticien> obtenirTousLesDieteticiens() {
        return dieteticienService.obtenirTousLesDieteticiens();
    }

 */
}