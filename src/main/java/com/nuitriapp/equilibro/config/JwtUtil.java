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

    private final RoleService roleService;
    private String secret = "maCleSecretePourJWT";

    public JwtUtil(RoleService roleService) {
        this.roleService = roleService;
    }

    // Générer un token JWT à partir de l'email et de l'ID utilisateur
    public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)           // Ajout de l'ID utilisateur dans les claims
                .claim("roles", roleService)    // Ajoutez les rôles si nécessaire
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expire dans 10 heures
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Extraire l'email du token JWT
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire l'ID utilisateur à partir du token JWT
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", Long.class);
    }

    // Extraire les rôles du token JWT
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    // Extraire une réclamation spécifique
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire toutes les réclamations (claims)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token a expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraire la date d'expiration du token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Valider le token en comparant l'email et l'expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
