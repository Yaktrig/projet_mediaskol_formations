package fr.mediaskol.projet.bo.formateur;


import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un formateur d'une session de formation dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à un formateur durant une session, telles que son identifiant,
 * le commentaire durant la session et le statut (si présence confirmée ou en attente).
 * </p>
 * <p>
 * Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
 * </p>
 *
 * @author Mélissa
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "SESSION_FORMATEUR")
public class SessionFormateur {

    // Todo  test association

    /**
     * Identifiant unique de la session formateur.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_FORMATEUR_ID")
    private Long idSessionFormateur;

    /**
     * Commentaire général à propos du formateur durant la session.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_SESSION_FORMATEUR", length = 2000)
    @Size(max = 2000, message = "{sessionFormateur.commentaireFormateur.size}")
    private String commentaireSessionFormateur;


    /**
     * Statut métier de la session formateur.
     * <p>
     * Ce champ indique l'état d'avancement de la session formateur
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionFormateur}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_SESSION_FORMATEUR")
    private StatutSessionFormateur statutSessionFormateur;


    /**
     * Formateur qui est rattaché la sessionFormateur.
     * <p>
     * Association Many-to-One vers l'entité {@link Formateur}.
     * Permet de centraliser les informations liées au formateur et d'assurer l'intégrité des données.
     * Plusieurs SessionFormateur peuvent être associées au même formateur.
     * </p>
     * <p>
     * Cette relation est obligatoire : une sessionFormateur doit avoir un formateur de renseigné.
     * La récupération du formateur est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMATEUR_ID", nullable = false)
    @NotNull(message = "{sessionFormateur.formateur.notnull}")
    private Formateur formateur;


    /**
     * Session de formation qui est rattachée à la sessionFormateur.
     * <p>
     * Association Many-to-One vers l'entité {@link SessionFormation}.
     * Permet de centraliser les informations liées à la session de formation et d'assurer l'intégrité des données.
     * Plusieurs SessionFormateur peuvent être associées à la même Session de formation.
     * </p>
     * <p>
     * Cette relation est obligatoire : une sessionFormateur doit avoir une session de formation de renseignée.
     * La récupération de la session de formation est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_FORMATION_ID", nullable = false)
    @NotNull(message = "{sessionFormateur.sessionFormation.notnull}")
    private SessionFormation sessionFormation;

}
