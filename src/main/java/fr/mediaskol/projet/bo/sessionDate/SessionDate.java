package fr.mediaskol.projet.bo.sessionDate;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente le(s) dates (en présentiel, en distanciel) dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session date,
 * telles que l'identifiant, la date,
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
@Table(name = "SESSION_DATE")
public class SessionDate {


    /**
     * Identifiant unique de la session date.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_DATE_ID")
    private Long idSessionDate;

    /**
     * Date de la session de formation
     * <p>
     * Généralement, il y a au moins deux dates de formation. En présentiel, la première date correspondra
     * à la date de début de session de formation. En distanciel, chaque date correspond au tuto en visio,
     * car il y en a plusieurs.
     * </p>
     */
    @Column(name = "DATE_SESSION", nullable = false)
    @NotNull(message = "{sessionDate.dateSession.notnull}")
    private LocalDate dateSession;



    /**
     * Nombre d'heures sur une journée en présentiel ou pour la visio.
     * <p>
     *     Ce champ est optionnel.
     * </p>
     */
    @Column(name = "DUREE")
    private Integer duree;

    /**
     * Heure du début de la visio.
     * <p>
     *     Ce champ est optionnel, car non nécessaire actuellement pour la formation en présentiel.
     * </p>
     */
    @Column(name="DEBUT_HEURE_VISIO")
    private LocalDateTime heureVisio;


    /**
     * Statut métier de la session date.
     * <p>
     * Ce champ indique l'état d'avancement de la facturation des salles pour une session et pour une date
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionDate}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name="STATUT_SESSION_DATE")
    private StatutSessionDate statutSessionDate;

    /**
     * SessionFormateur qui est rattachée la sessionDate.
     * <p>
     * Association Many-to-One vers l'entité {@link SessionFormateur}.
     * Permet de centraliser les informations liées à la SessionFormateur et d'assurer l'intégrité des données.
     * Plusieurs SessionDate peuvent être associées à la même SessionFormateur.
     * </p>
     * <p>
     * Cette relation est optionnelle : une sessionFormateur n'est pas toujours, au départ, affectée une SessionDate.
     * La récupération de la SessionFormateur est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_FORMATEUR_ID")
    private SessionFormateur sessionFormateur;


    /**
     * SessionFormation qui est rattachée la sessionDate.
     * <p>
     * Association Many-to-One vers l'entité {@link SessionFormation}.
     * Permet de centraliser les informations liées à la sessionFormation et d'assurer l'intégrité des données.
     * Plusieurs SessionDate peuvent être associées à la même SessionFormation.
     * </p>
     * <p>
     * Cette relation est obligatoire : une sessionFormation doit avoir au moins une SessionDate de renseignée.
     * La récupération de la SessionFormation est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_FORMATION_ID", nullable = false)
    @NotNull(message="{sessionDate.sessionFormation.notnull")
    private SessionFormation sessionFormation;



    /**
     * SessionSalle qui est rattachée la sessionDate.
     * <p>
     * Association Many-to-One vers l'entité {@link SessionSalle}.
     * Permet de centraliser les informations liées à la SessionSalle et d'assurer l'intégrité des données.
     * Plusieurs SessionDate peuvent être associées à la même SessionSalle.
     * </p>
     * <p>
     * Cette relation est optionnelle : une sessionSalle n'est pas toujours affectée une SessionDate (formation
     * en distanciel par exemple).
     * La récupération de la SessionSalle est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_SALLE_ID")
    private SessionSalle sessionSalle;

}
