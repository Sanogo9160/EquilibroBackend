package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Administrateur;
import com.nuitriapp.equilibro.model.Role;
import com.nuitriapp.equilibro.repository.AdministrateurRepository;
import com.nuitriapp.equilibro.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InitialisateurDeDonnees implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
        // Création des rôles s'ils n'existent pas
        creerRoleSiInexistant("ADMIN");
        creerRoleSiInexistant("DIETETICIEN");
        creerRoleSiInexistant("UTILISATEUR");

        // Création d'un administrateur par défaut s'il n'existe pas
        String adminEmail = "admin@equilibro.com";
        Optional<Administrateur> administrateurOpt = administrateurRepository.findByEmail(adminEmail);
        if (administrateurOpt.isEmpty()) {
            Role roleAdmin = roleRepository.findByNom("ADMIN").orElse(null);

            Administrateur admin = new Administrateur();
            admin.setNom("Administrateur");
            admin.setEmail(adminEmail);
            admin.setTelephone("77004020");
            admin.setMotDePasse(passwordEncoder.encode("admin@24")); // Mot de passe par défaut (à changer)
            admin.setRole(roleAdmin);

            // Définir les attributs nécessaires pour l'administrateur
            admin.setPoids(70.0); // kg
            admin.setTaille(175.0); // cm
            admin.setAge(30); // années
            admin.setSexe("Femme"); // ou "Homme"

            administrateurRepository.save(admin);
            System.out.println("Administrateur par défaut créé avec l'email: " + adminEmail);
        } else {
            System.out.println("Administrateur par défaut déjà existant.");
        }
    }

        private void creerRoleSiInexistant(String nom) {
        Role role = roleRepository.findByNom(nom).orElse(null);
        if (role == null) {
            role = new Role();
            role.setNom(nom);
            roleRepository.save(role);
            System.out.println("Rôle " + nom + " créé.");
        }
    }
}
