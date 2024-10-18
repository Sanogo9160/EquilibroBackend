package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Historique;
import com.nuitriapp.equilibro.repository.HistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueService {

    @Autowired
    private HistoriqueRepository historiqueRepository;

    // Récupérer tous les historiques
    public List<Historique> obtenirTousLesHistoriques() {
        return historiqueRepository.findAll();
    }

    // Récupérer un historique par son ID
    public Optional<Historique> obtenirHistoriqueParId(Long id) {
        return historiqueRepository.findById(id);
    }

    // Créer un nouvel historique
    public Historique creerHistorique(Historique historique) {
        return historiqueRepository.save(historique);
    }

    // Supprimer un historique par son ID
    public void supprimerHistorique(Long id) {
        historiqueRepository.deleteById(id);
    }

}