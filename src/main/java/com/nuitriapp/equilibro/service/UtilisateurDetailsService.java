package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email: " + email));

        // Récupére le rôle de l'utilisateur
       // String role = utilisateur.getRole().getNom();

        // Crée une liste d'autorités (rôles)
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (utilisateur.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));
        }

        return new User(utilisateur.getEmail(), utilisateur.getMotDePasse(), authorities);
    }
}