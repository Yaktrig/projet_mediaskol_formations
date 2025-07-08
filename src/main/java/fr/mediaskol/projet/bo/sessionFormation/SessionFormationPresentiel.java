package fr.mediaskol.projet.bo.sessionFormation;

import fr.mediaskol.projet.bo.departement.Departement;
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
    @Builder.Default
    @Column(name = "STATUT_YODA", nullable = false, length = 5)
    private String statutYoda = "DO";


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

    /**
     * Département auquel est rattachée la session de formation en présentiel.
     * <p>
     * Association Many-to-One vers l'entité {@link Departement}.
     * Permet de centraliser les informations liées au département (numéro, nom, région, couleur, etc.)
     * et d'assurer l'intégrité des données. Plusieurs adresses peuvent être associées au même département.
     * </p>
     * <p>
     * Cette relation est obligatoire : une session de formation en présentiel doit avoir un numéro de département renseigné.
     * La récupération du département est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUM_DEPARTEMENT", nullable = false)
    private Departement departement;
}
