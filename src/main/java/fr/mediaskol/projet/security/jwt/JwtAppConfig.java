package fr.mediaskol.projet.security.jwt;


import fr.mediaskol.projet.dal.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class JwtAppConfig {

    /**
     * Authentification et détail de l'utilisateur depuis la base de données
     */
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Utilisation d'un lambda pour lui dire que je vais utiliser le repository pour chercher mon pseudo
    // s'il n'y a pas de pseudo, alors je lève une exception
    @Bean
    UserDetailsService userDetailsService() {

        return username -> utilisateurRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
