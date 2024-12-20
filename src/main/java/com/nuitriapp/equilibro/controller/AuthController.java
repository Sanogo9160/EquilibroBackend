package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.config.JwtUtil;
import com.nuitriapp.equilibro.model.AuthRequest;
import com.nuitriapp.equilibro.model.AuthResponse;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.service.UtilisateurDetailsService;
import com.nuitriapp.equilibro.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:54893"})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurDetailsService utilisateurDetailsService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getMotDePasse())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }

        final UserDetails userDetails = utilisateurDetailsService.loadUserByUsername(authRequest.getEmail());

        // Utilisation de UtilisateurService pour obtenir l'utilisateur par email
        Utilisateur utilisateur = utilisateurService.rechercherParEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), utilisateur.getId());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
