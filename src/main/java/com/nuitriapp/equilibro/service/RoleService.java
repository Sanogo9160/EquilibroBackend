package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Role;
import com.nuitriapp.equilibro.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public List<Role> obtenirTousLesRoles() {
        return roleRepository.findAll();
    }

    public Role obtenirRoleParId(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role ajouterRole(Role role) {
        return roleRepository.save(role);
    }

    public Role mettreAJourRole(Long id, Role role) {
        Role existant = roleRepository.findById(id).orElse(null);
        if (existant != null) {
            existant.setNom(role.getNom());
            return roleRepository.save(existant);
        }
        return null;
    }

    public void supprimerRole(Long id) {
        roleRepository.deleteById(id);

    }
}
