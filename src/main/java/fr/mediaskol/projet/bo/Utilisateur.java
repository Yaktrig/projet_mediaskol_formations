package fr.mediaskol.projet.bo;

import fr.mediaskol.projet.bo.salarie.Salarie;
import jakarta.persistence.*;
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
public class Utilisateur implements UserDetails {

    private static final long serialVersionUID = 1L;


    /**
     * Le pseudo correspond à l'email du salarié
     */
    @Id
    @Column(name = "LOGIN", nullable = false, length = 255)
    private String pseudo;


    /**
     * Mot du passe pour que le salarié puisse se connecter.
     * <ul>
     *    <li>Le mot de passe n'est pas obligatoire si le statut de l'inscription est non actif.</li>
     *    <li>Il est limité à 68 caractères.</li>
     * </ul>
     */
    @Column(name = "PASSWORD", nullable = false, length = 68)
    private String password;

    /**
     * Rôle associé au salarié.
     * <p>
     * Permet de donner des droits d'accès à certaines fonctionnalités et pages, en fonction du rôle attribué.
     * Ce champ est obligatoire et limité à 50 caractères.
     * </p>
     */
    @Column(name="ROLE", length = 15, nullable = false)
    private String authority;


    /**
     * Association OneToOne entre l'utilisateur et le salarié
     * Un utilisateur est un salarié - ils ont le même mail
     */
    @OneToOne
    @JoinColumn(name="SALARIE_ID")
    private Salarie salarie;

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
