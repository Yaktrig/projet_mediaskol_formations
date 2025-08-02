package fr.mediaskol.projet.controller.security;

import fr.mediaskol.projet.dto.security.AuthenticationRequest;
import fr.mediaskol.projet.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mediaskolFormation")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/auth")
    public Map<String, String> authenticate(@RequestBody AuthenticationRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getPseudo(), req.getPassword())
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(ga -> ga.getAuthority().replace("ROLE_", "")) // ou laisse tel quel selon ta config JWT
                .collect(Collectors.toList());

        // Exemple statique
//        String username = req.getPseudo();
//        List<String> roles = List.of("SALARIE", "ADMIN");

        String token = jwtService.generateToken(userDetails.getUsername(), roles);
        return Map.of("token", token);
    }

}
