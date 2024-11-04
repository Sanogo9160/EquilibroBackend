package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ListeDeCourses;
import com.nuitriapp.equilibro.model.PlanRepas; // Assurez-vous d'avoir cette classe définie
import com.nuitriapp.equilibro.service.PlanRepasService;
import com.nuitriapp.equilibro.service.ProfilDeSanteService; // Assurez-vous que ce service est défini
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/liste-courses")
public class ListeDeCoursesController {

    @Autowired
    private PlanRepasService planRepasService;

    @Autowired
    private ProfilDeSanteService profilDeSanteService; // Service pour récupérer le profil de santé

    @GetMapping("/{profilId}")
    public ResponseEntity<ListeDeCourses> genererListeDeCourses(@PathVariable Long profilId) {
        // Obtenir le plan de repas de l'utilisateur
        PlanRepas planRepas = obtenirPlanRepasParProfilId(profilId);

        // Vérifiesi le plan de repas existe
        if (planRepas == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Générez la liste de courses
        ListeDeCourses listeDeCourses = planRepasService.genererListeDeCourses(planRepas);
        return new ResponseEntity<>(listeDeCourses, HttpStatus.OK);
    }

    // méthode pour récupérer le PlanRepas à partir de l'ID de profil
    private PlanRepas obtenirPlanRepasParProfilId(Long profilId) {
        // service ProfilDeSante pour obtenir le plan de repas associé au profil
        return profilDeSanteService.genererPlanRepas(profilId); // Méthode à vérifier dans le service
    }
}
