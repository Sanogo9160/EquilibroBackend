package com.nuitriapp.equilibro.config;
import com.nuitriapp.equilibro.service.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final RoleService role;
    // Clé secrète pour signer le JWT
    private String secret = "maCleSecretePourJWT";

    public JwtUtil(RoleService role) {
        this.role = role;
    }

    // Générer un token JWT à partir de l'email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // L'email est le sujet
                .claim("roles", role)
                .setIssuedAt(new Date()) // Date de génération du token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration dans 10 heures
                .signWith(SignatureAlgorithm.HS256, secret) // Signature avec la clé secrète et l'algorithme HS256
                .compact();
    }

    // Extraire l'email à partir du token JWT
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); // Le sujet contient l'email
    }

    // Extraire les rôles à partir du token JWT
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);  // Récupérer les rôles depuis les claims du JWT
    }


    // Extraire une réclamation spécifique (ici l'email)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire toutes les réclamations (claims) à partir du token JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token JWT a expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraire la date d'expiration du token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Valider le token JWT en vérifiant l'email et l'expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
