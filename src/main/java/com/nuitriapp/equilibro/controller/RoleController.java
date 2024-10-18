package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Role;
import com.nuitriapp.equilibro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/liste")
    public List<Role> obtenirTousLesRoles() {
        return roleService.obtenirTousLesRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> obtenirRoleParId(@PathVariable Long id) {
        Role role = roleService.obtenirRoleParId(id);
        return role != null ? new ResponseEntity<>(role, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Role> ajouterRole(@RequestBody Role role) {
        Role nouveauRole = roleService.ajouterRole(role);
        return new ResponseEntity<>(nouveauRole, HttpStatus.CREATED);
    }

    @PutMapping("/mettreAJour/{id}")
    public ResponseEntity<Role> mettreAJourRole(@PathVariable Long id, @RequestBody Role role) {
        Role roleMisAJour = roleService.mettreAJourRole(id, role);
        return roleMisAJour != null ? new ResponseEntity<>(roleMisAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerRole(@PathVariable Long id) {
        roleService.supprimerRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
