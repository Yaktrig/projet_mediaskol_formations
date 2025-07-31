package fr.mediaskol.projet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mediaskolFormation")
public class MediaskolController {

    /**
     * WebService qui retourne une chaîne de caractères
     * <p>
     *     Toutes les url qui s'appellent mediaskolFormation vont être redirigées vers la classe et la méthode
     * </p>
     */
    @GetMapping
    public String welcomeAPI(){

        return "Bienvenue sur l'API Mediaskol";
    }

    @GetMapping("/auth/test")
    public ResponseEntity<?> testAuth(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non authentifié");
        }
        return ResponseEntity.ok(Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        ));
    }

}
