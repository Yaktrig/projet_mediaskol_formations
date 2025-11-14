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

/**
 * Configuration de la sécurité de l’application Mediaskol.
 * <p>
 * Cette classe configure l’authentification JWT, les filtres de sécurité,
 * et définit les règles d’accès selon les rôles des utilisateurs.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class MediaskolSecurityConfig {

    /**
     * Expose l’AuthenticationManager fourni par Spring Security.
     *
     * @param authConfig configuration d’authentification fournie par Spring
     * @return AuthenticationManager prêt à l’emploi
     * @throws Exception en cas d’erreur de configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Définit les règles d’autorisation pour les endpoints HTTP
     * et configure la protection CSRF et l’authentification JWT.
     *
     * @param http objet de configuration pour le filtre HTTP
     * @return SecurityFilterChain construit
     * @throws Exception en cas d’erreur lors de la construction du filtre
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Définition des règles d’accès
                .authorizeHttpRequests(auth -> auth
                        // Documentation Swagger accessible sans authentification
                        .requestMatchers("/mediaskol/swagger-ui.html",
                                "/mediaskol/swagger-ui/**",
                                "/mediaskol/api-docs/**").permitAll()
                        // Endpoints d’authentification publics
                        .requestMatchers("/mediaskolFormation/auth/**").permitAll()
                        // Accès REST restreint selon roles SALARIE et ADMIN
                        .requestMatchers("/mediaskolFormation/formations/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mediaskolFormation/sessionsFormationsPresentiels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers("/mediaskolFormation/sessionsFormationsDistanciels/**").hasAnyRole("SALARIE", "ADMIN")
                        .requestMatchers("/mediaskolFormation/finsSessionsFormations/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/departements/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/apprenants/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/sessionsApprenants/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/salles/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/sessionsSalles/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/formateurs/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/sessionsFormateurs/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/sessionsDates/**").hasAnyRole("ADMIN", "SALARIE")
                        .requestMatchers("/mediaskolFormation/salaries/**").hasAnyRole("ADMIN")
                        // Tous les autres endpoints nécessitent une authentification
                        .anyRequest().authenticated()
                )
                // Désactivation du  CSRF pour utiliser des tokens JWT stateless
                .csrf(csrf -> csrf.disable())
                // Configuration du resource server pour JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    /**
     * Configure la conversion des rôles stockés dans le JWT
     * pour l’utilisation par Spring Security.
     *
     * @return JwtAuthenticationConverter personnalisé
     */
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