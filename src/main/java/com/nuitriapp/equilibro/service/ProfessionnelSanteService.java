package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ProfessionnelSante;
import com.nuitriapp.equilibro.repository.ProfessionnelSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionnelSanteService {

    @Autowired
    private ProfessionnelSanteRepository professionnelSanteRepository;

    // Lister tous les professionnels de santé
    public List<ProfessionnelSante> listerProfessionnelsSante() {
        return professionnelSanteRepository.findAll();
    }

    // Obtenir un professionnel de santé par ID
    public Optional<ProfessionnelSante> obtenirProfessionnelSanteParId(Long id) {
        return professionnelSanteRepository.findById(id);
    }

    // Créer un nouveau professionnel de santé
    public ProfessionnelSante creerProfessionnelSante(ProfessionnelSante professionnelSante) {
        return professionnelSanteRepository.save(professionnelSante);
    }

    // Supprimer un professionnel de santé par ID
    public void supprimerProfessionnelSante(Long id) {
        professionnelSanteRepository.deleteById(id);
    }

    // Modifier un professionnel de santé existant
    public ProfessionnelSante modifierProfessionnelSante(Long id, ProfessionnelSante professionnelSanteModifie) {
        Optional<ProfessionnelSante> professionnelExistante = professionnelSanteRepository.findById(id);

        if (professionnelExistante.isPresent()) {
            ProfessionnelSante professionnel = professionnelExistante.get();
            professionnel.setNom(professionnelSanteModifie.getNom());
            professionnel.setSpecialite(professionnelSanteModifie.getSpecialite());
            professionnel.setContact(professionnelSanteModifie.getContact());
            return professionnelSanteRepository.save(professionnel);
        } else {
            throw new RuntimeException("Professionnel de santé non trouvé pour l'ID : " + id);
        }
    }


}
