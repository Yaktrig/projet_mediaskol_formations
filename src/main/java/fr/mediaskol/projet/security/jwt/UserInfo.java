package fr.mediaskol.projet.security.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class UserInfo implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * Le pseudo correspond à l'email du salarié
     */
    @Id
    @Column(length = 250)
    private String pseudo;

    /**
     * Mot du passe pour que le salarié puisse se connecter.
     * <ul>
     *    <li>Le mot de passe n'est pas obligatoire si le statut de l'inscription est non actif.</li>
     *    <li>Il est limité à 68 caractères.</li>
     * </ul>
     */
    @Column(nullable = false, length = 68)
    private String password;

    /**
     * Rôle associé au salarié.
     * <p>
     * Permet de donner des droits d'accès à certaines fonctionnalités et pages, en fonction du rôle attribué.
     * Ce champ est obligatoire et limité à 50 caractères.
     * </p>
     */
    @Column(length = 15, nullable = false)
    private String authority;


    /**
     * Rôles de l'utilisateur
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority));
    }

    /**
     * Correspond à l'élément d'authentification
     *
     * @return
     */
    @Override
    public String getUsername() {
        return pseudo;
    }


    /**
     * Etat du compte utilisateur - compte non expiré
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Etat du compte utilisateur - non vérouilllé
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indique si les informations d'identification sont non expirées
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Etat du compte utilisateur - actif
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
