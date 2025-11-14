package fr.mediaskol.projet.security;

import jakarta.servlet.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class MediaksolSecurityConfig {


    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private Filter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // habilitation - utilisation d'un lambda
        http.authorizeHttpRequests(auth -> {
                    auth
                            // permettre l'accès à l'URL racine à tout le monde
                            //.requestMatchers("/").permitAll()

                            // permettre l'accès à l'url de la documentatin des API (avec swagger)
                            .requestMatchers("/mediaskol/swagger-ui.html",
                                    "/mediaskol/swagger-ui/**",
                                    "/mediaskol/api-docs/**").permitAll()

                            .requestMatchers("/mediaskolFormation/auth/**").permitAll()

                              // permettre aux rôles EMPLOYE et ADMIN de manipuler les URLs en GET
                            .requestMatchers(HttpMethod.GET,"/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")

                            .requestMatchers("/mediaskolFormation/sessionsFormationsDistanciels/**").hasAnyRole("SALARIE", "ADMIN")
                            .requestMatchers("/mediaskolFormation/formations/**").hasAnyRole("SALARIE", "ADMIN")
                            .requestMatchers("/mediaskolFormation/salaries/**").hasAnyRole("ADMIN", "SALARIE")
                            .requestMatchers("/mediaskolFormation/departements/**").hasAnyRole("ADMIN", "SALARIE")
                            // Toutes les autres url et méthodes HTTP ne sont pas permises
                            .anyRequest().denyAll();
                }
        );

        // Désactiver Cross Site Request Forgery
        // Non préconisé pour les API REST en stateless. Sauf pour POST, PUT, PATCH et DELETE
        http.csrf(AbstractHttpConfigurer::disable);

        // Connexion de l'utilisateur
        http.authenticationProvider(authenticationProvider);

        // Activer le filtre JWT et l'authentification de l'utilisateur
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // session Stateless
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }
}
