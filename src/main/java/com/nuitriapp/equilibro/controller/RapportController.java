package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping("/{profilId}")
    public ResponseEntity<String> genererRapport(@PathVariable Long profilId) {
        String rapport = rapportService.genererRapport(profilId);
        return new ResponseEntity<>(rapport, HttpStatus.OK);
    }
}
