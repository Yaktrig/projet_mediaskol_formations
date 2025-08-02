package fr.mediaskol.projet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MediaskolSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Accès libre à la documentation swagger
                        .requestMatchers("/mediaskol/swagger-ui.html",
                                "/mediaskol/swagger-ui/**",
                                "/mediaskol/api-docs/**").permitAll()
                        // Accès libre à l'authentification
                        .requestMatchers("/mediaskolFormation/auth/**").permitAll()
                        // Autoriser l'accès GET et POST aux bonnes URL pour les rôles concernés
                        .requestMatchers(HttpMethod.GET, "/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers("/mediaskolFormation/sessionsFormationsDistanciels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers("/mediaskolFormation/formations/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers("/mediaskolFormation/salaries/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/departements/**").hasAnyRole("ADMIN", "SALARIE")
                        .anyRequest().authenticated()
                )
                // Désactive CSRF
                .csrf(csrf -> csrf.disable())
                // Configuration de l'authentification JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
}