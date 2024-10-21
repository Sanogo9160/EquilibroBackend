package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.dto.ConsultationDTO;
import com.nuitriapp.equilibro.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/reserver")
    public String reserverConsultation(@RequestBody ConsultationDTO consultationDTO) {
        consultationService.bookConsultation(consultationDTO);
        return "Consultation réservée avec succès.";
    }

    @PostMapping("/confirmer/{id}")
    public String confirmerConsultation(@PathVariable Long id) {
        consultationService.confirmerConsultation(id);
        return "Consultation confirmée.";
    }

}
