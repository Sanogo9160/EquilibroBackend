package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.dto.DisponibiliteDTO;
import com.nuitriapp.equilibro.model.Disponibilite;
import com.nuitriapp.equilibro.model.Dieteticien;
import com.nuitriapp.equilibro.repository.DisponibiliteRepository;
import com.nuitriapp.equilibro.repository.DieteticienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibiliteService {

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Autowired
    private DieteticienRepository dieteticienRepository;

    /*
    public void ajouterDisponibilite(DisponibiliteDTO disponibiliteDTO) {
        Dieteticien dieteticien = dieteticienRepository.findById(disponibiliteDTO.getDieteticienId())
                .orElseThrow(() -> new RuntimeException("Dieteticien non trouvé"));

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setDieteticien(dieteticien);
        disponibilite.setDateDebut(disponibiliteDTO.getDateDebut());
        disponibilite.setDateFin(disponibiliteDTO.getDateFin());

        disponibiliteRepository.save(disponibilite);
    }

    public List<DisponibiliteDTO> getDisponibilitesByDieteticien(Long dieteticienId) {
        List<Disponibilite> disponibilites = disponibiliteRepository.findByDieteticienId(dieteticienId);
        return disponibilites.stream().map(DisponibiliteDTO::fromEntity).collect(Collectors.toList());
    }
     */

    public void ajouterDisponibilite(DisponibiliteDTO disponibiliteDTO) {
        Disponibilite disponibilite = disponibiliteDTO.toEntity();
        disponibiliteRepository.save(disponibilite);
    }

    public List<DisponibiliteDTO> getDisponibilitesByDieteticien(Long dieteticienId) {
        List<Disponibilite> disponibilites = disponibiliteRepository.findByDieteticienId(dieteticienId);
        return disponibilites.stream()
                .map(DisponibiliteDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
