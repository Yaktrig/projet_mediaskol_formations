package fr.mediaskol.projet.bo.sessionFormation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une session de formation dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation en présentiel,
 * telles que l'identifiant, le numéro AF (Yoda) unique, le libellé, le statut, le numéro du département concerné,
 * la couleur associée au numéro du département et le statut métier de la session
 * </p>
 * <p>
 * Utilisation de Lombok (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor) pour générer
 * automatiquement les méthodes usuelles (getters, setters, constructeurs, toString, equals, hashCode).
 * </p>
 *
 * @author Mélissa
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "SESSION_FORMATION")
public class SessionFormationPresentiel {

    /**
     * Identifiant unique de la session de formation.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_FORMATION_ID")
    private Long idSessionFormation;

    /**
     * Numéro AF (Yoda) unique de la session de formation.
     * <p>
     * Ce numéro est obligatoire et doit être unique pour chaque session.
     * Il est limité à 30 caractères
     * </p>
     */
    @Column(name = "NO_AF_YODA", unique = true, nullable = false, length = 30)
    private Long noYoda;

    /**
     * Libellé de la session de formation.
     * <p>
     * Généralement composé du thème et de la date du premier jour de la session.
     * Ce champ est optionnel à la création de la session de formation et limité à 50 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_SESSION_FORMATION", nullable = true, length = 50)
    private String libelleSessionFormation;


    /**
     * Statut Yoda de la session de formation.
     * <p>
     * Indique l'état d'avancement du financement par Ipéria :
     * <ul>
     *   <li>DO : Demande en cours</li>
     *   <li>ATT : En attente de validation</li>
     *   <li>E : Engagé</li>
     * </ul>
     * La valeur par défaut est "DO".
     * </p>
     */
    @Column(name = "STATUT_YODA", nullable = false, length = 5)
    private String statutYoda = "DO";


    /**
     * Numéro du département où se déroule la session de formation.
     * <p>
     * Actuellement utilisé pour la Bretagne. Ce champ est optionnel à la création de la session.
     * </p>
     */
    @Column(name = "NO_DEPARTEMENT", nullable = true, length = 3)
    private Integer noDepartementSession;


    /**
     * Couleur associée au numéro du département.
     * <p>
     * Utilisé pour l'affichage ou la catégorisation des sessions par département.
     * Ce champ est optionnel et limité à 20 caractères.
     * </p>
     */
    @Column(name = "COULEUR_DEPARTEMENT", nullable = true, length = 20)
    private String couleurDepartementSession;


    /**
     * Statut métier de la session de formation.
     * <p>
     * Ce champ indique l'état d'avancement de la session de formation ou de son dossier
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionFormationPresentiel}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private StatutSessionFormationPresentiel statutSessionFormationPresentiel;

    // TODO créer l'association entre SessionFormationPresentiel et Formation - OneToMany
    // TODO créer l'association entre SessionFormationPresentiel et Departement - OneToMany
}
