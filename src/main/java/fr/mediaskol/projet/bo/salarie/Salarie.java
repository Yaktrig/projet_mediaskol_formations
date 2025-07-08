package fr.mediaskol.projet.bo.salarie;

import fr.mediaskol.projet.bo.Personne;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Représente un salarié dans le système de gestion.
 * <p>
 * Cette entité hérite de {@link Personne} et contient les informations spécifiques à un salarié,
 * telles que ses coordonnées, son mot de passe, la couleur associée, son rôle et le statut de son inscription.
 * </p>
 * <p>
 * Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
 * </p>
 *
 * @author Mélissa
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder

@Entity
@Table(name="SALARIE")
public class Salarie extends Personne {

    /**
     * Mot du passe pour que le salarié puisse se connecter.
     * <ul>
     *    <li>Le mot de passe n'est pas obligatoire si le statut de l'inscription est non actif.</li>
     *    <li>Il est limité à 68 caractères.</li>
     * </ul>
     */
    @Column(name="MOT_DE_PASSE", nullable = true, length = 68)
    private String mdp;

    /**
     * Couleur hexadécimale associée au salarié.
     * <p>
     * Permet aux utilisateurs de visualiser qui traite les dossiers liés aux sessions de formations.
     * Ce champ est obligatoire et limité à 7 caractères.
     * </p>
     */
    @Column(name="COULEUR_SALARIE", nullable = false, length = 7)
    private String couleurSalarie;

    /**
     * Rôle associé au salarié.
     * <p>
     * Permet de donner des droits d'accès à certaines fonctionnalités et pages, en fonction du rôle attribué.
     * Ce champ est obligatoire et limité à 50 caractères.
     * </p>
     */
    @Column(name="ROLE_SALARIE", nullable = false, length = 50)
    private String roleSalarie;

    /**
     * Statut de l'inscription à l'espace de connexion du salarié.
     * <ul>
     *  <li>L'administrateur peut créer un compte pour un salarié et le mettre en statut inactif.</li>
     *  <li>Ensuite le salarié pourra, en ajoutant son mot de passe, rendre actif son compte utilisateur.</li>
     *  <li> 0 : Compte inactif — pas de mot de passe</li>
     *  <li> 1 : Compte actif — mot de passe présent</li>
     * </ul>
     * Ce champ est un booléen.
     */
    @Column(name="STATUT_INSCRIPTION", nullable = false)
    private boolean statutInscription;


}
