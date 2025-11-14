package fr.mediaskol.projet.security.jwt;

import fr.mediaskol.projet.bo.Utilisateur;
import fr.mediaskol.projet.dal.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private UtilisateurRepository utilisateurRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getPassword()));

        Utilisateur user = utilisateurRepository.findById(request.getPseudo()).orElseThrow();

        String jwtToken = jwtService.generateToken(user.getUsername(), user.getRoles());
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

}
