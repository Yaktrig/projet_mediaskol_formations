package fr.mediaskol.projet.bo.SessionFormation;


import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * Représente une session de formation (en présentiel, en distanciel) dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation,
 * telles que l'identifiant, le numéro AF (Yoda) unique, le libellé, le statut Yoda, le numéro du département concerné
 * pour le présentiel, la couleur associée au numéro du département et le statut métier de la session.
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
public class SessionFormation {

    // Todo  test association

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
     * Ce numéro n'est pas obligatoire, car à la création d'une session de formation pour l'année suivante, il
     * n'existe pas encore de numéro attribué par Ipéria.
     * Il est limité à 30 caractères.
     * </p>
     */
    @Column(name = "NO_AF_YODA", unique = true, length = 30)
    @Size(max = 30, message = "{formation.numAfYoda.size}")
    private String noYoda;

    /**
     * Libellé de la session de formation.
     * <p>
     * Formation en présentiel : composé du thème, des premières lettres du lieu de formation et de la date du premier
     * jour de la session.
     * Formation en distanciel : composé du thème, FOAD et date du premier jour de la session
     * Ce champ est optionnel à la création de la session de formation et limité à 50 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_SESSION_FORMATION", length = 50)
    @Size(max = 50, message="{sessionFormation.libelle.size}")
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
    @Size(min=1, max = 5, message = "{sessionFormation.statutYoda.size}")
    @NotNull(message = "{sessionFormation.statutYoda.notnull}")
    @NotBlank(message = "{sessionFormation.statutYoda.notblank}")
    private String statutYoda = "DO";

    /**
     * Date du début de la session de formation
     * <p>
     *     Permet de savoir quand commence la formation et permet pour le distanciel d'afficher
     *     le début et la fin de formation (01/mm/yyyy au 30(31)/mm/yyyy
     * </p>
     */
    @Column(name="DATE_DEBUT_SESSION", nullable = false)
    @NotNull(message = "{sessionFormation.dateDebutSession.notnull}")
    private LocalDate dateDebutSession;

    /**
     * Nombre total d'heures pour la session de formation
     * <p>
     *     Ce champ est obligatoire pour les formations en présentiel et en distanciel.
     * </p>
     */
    @Column(name="NB_HEURE_SESSION", nullable = false)
    @NotNull(message = "{sessionFormation.nbHeureSession.notnull}")
    private Integer nbHeureSession;


    /**
     * Statut métier de la session de formation.
     * <p>
     * Ce champ indique l'état d'avancement de la session de formation ou de son dossier
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionFormation}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name="STATUT_SESSION_FORMATION", nullable = false)
    @NotNull(message="{sessionFormation.statutSessionFormation.notnull}")
    @Builder.Default
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;

    /**
     * Formation à laquelle est rattachée la session de formation.
     * <p>
     * Association Many-to-One vers l'entité {@link Formation}.
     * Permet de centraliser les informations liées à la formation (thème et libellé)
     * et d'assurer l'intégrité des données. Plusieurs Sessions peuvent être associées à la même formation.
     * </p>
     * <p>
     * Cette relation est obligatoire : une session de formation est obligée d'avoir une formation de renseignée.
     * La récupération de la formation est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMATION_ID")
    @NotNull(message = "{sessionFormation.formation.notnull }")
    private Formation formation;


    /**
     * Département auquel est rattachée la session de formation (en présentiel).
     * <p>
     * Association Many-to-One vers l'entité {@link Departement}.
     * Permet de centraliser les informations liées au département (numéro, nom, région, couleur, etc.)
     * et d'assurer l'intégrité des données. Plusieurs Sessions peuvent être associées au même département.
     * </p>
     * <p>
     * Cette relation est facultative : une session de formation en distanciel n'a pas de numéro de département renseigné.
     * La récupération du département est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUM_DEPARTEMENT")
    private Departement departement;

    /**
     * SessionFinFormation associée à SessionFormation
     * <p>
     * Association unidirectionnelle OneToOne avec {@link FinSessionFormation}
     * La suppression de la session de formation entraîne la suppression de la fin de session de formation
     * (cascade et orphanRemoval).
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "FIN_SESSION_FORMATION_ID")
    private FinSessionFormation finSessionFormation;

    /**
     * SessionFormationDistanciel associée à SessionFormation
     * <p>
     * Association unidirectionnelle OneToOne avec {@link SessionFormationDistanciel}
     * La suppression de la session de formation entraîne la suppression de la session de formation en distanciel
     * (cascade et orphanRemoval).
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "SESSION_FOAD_ID")
    private SessionFormationDistanciel sessionFormationDistanciel;




}
