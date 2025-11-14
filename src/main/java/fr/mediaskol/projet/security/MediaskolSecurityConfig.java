package fr.mediaskol.projet.security;

import fr.mediaskol.projet.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public MediaskolSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                                   AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Définit les règles d'autorisation pour les endpoints HTTP
     * et configure la protection CSRF et l'authentification JWT.
     *
     * @param http objet de configuration pour le filtre HTTP
     * @return SecurityFilterChain construit
     * @throws Exception en cas d'erreur lors de la construction du filtre
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Désactivation du CSRF pour utiliser des tokens JWT stateless
                .csrf(csrf -> csrf.disable())

                // Définition des règles d'accès
                .authorizeHttpRequests(auth -> auth
                        // Documentation Swagger accessible sans authentification
                        .requestMatchers("/mediaskol/swagger-ui.html",
                                "/mediaskol/swagger-ui/**",
                                "/mediaskol/api-docs/**").permitAll()
                        // Endpoints d'authentification publics
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
                        .requestMatchers("/mediaskolFormation/salaries/**").hasRole("ADMIN")
                        // Tous les autres endpoints nécessitent une authentification
                        .anyRequest().authenticated()
                )

                // Configuration de la gestion de session : stateless pour JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Ajout du provider d'authentification
                .authenticationProvider(authenticationProvider)

                // Ajout du filtre JWT avant le filtre d'authentification standard
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}