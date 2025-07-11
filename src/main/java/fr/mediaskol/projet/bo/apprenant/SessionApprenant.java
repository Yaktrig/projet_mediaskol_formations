package fr.mediaskol.projet.bo.apprenant;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formateur.Formateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une inscription d'un apprenant à une session de formation dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à un apprenant durant une session, telles que son identifiant
 * d'inscription, le commentaire durant la session, le statut (inscrit sur Yoda, présence ou absence à la session),
 * le moyen utilisé pour s'inscrire (mail, courrier), l'association à l'apprenant, l'association à la session
 * de formation.
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
@Table(name = "SESSION_APPRENANT")
public class SessionApprenant {

    // Todo Test associations
    /**
     * Identifiant unique de la session apprenant.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_APPRENANT_ID")
    private Long idSessionApprenant;

    /**
     * Commentaire général à propos de l'apprenant durant la session.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_SESSION_APPRENANT", length = 2000)
    @Size(max = 2000, message = "{sessionApprenant.commentaireSessionApprenant.size}")
    private String commentaireSessionApprenant;

    /**
     * Mode de réception de l'inscription à la session de formation (par mail, par courrier). Permet aux salariés
     * de voir par quel biais l'inscription a été réalisée.
     * <p>
     * Ce champ est obligatoire et est limité à 20 caractères.
     * </p>
     */
    @Column(name = "MODE_RECEPTION_INSCRIPTION", length = 20, nullable = false)
    @Size(min=3, max = 20, message = "{sessionApprenant.modeReceptionInscription.size}")
    @NotNull(message = "{sessionApprenant.modeReceptionInscription.notnull}")
    @NotBlank(message = "{sessionApprenant.modeReceptionInscription.notblank}")
    private String modeReceptionInscription;


    /**
     * Statut métier de l'inscription de l'apprenant.
     * <p>
     * Ce champ indique l'état d'avancement de l'inscription d'un apprenant à une session (inscrite sur Yoda, présence,
     * absence).
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionApprenant}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_SESSION_APPRENANT")
    private StatutSessionApprenant statutSessionApprenant;



    /**
     * Apprenant qui est rattaché la sessionApprenant.
     * <p>
     * Association Many-to-One vers l'entité {@link Apprenant}.
     * Permet de centraliser les informations liées à l'apprenant et d'assurer l'intégrité des données.
     * Plusieurs SessionApprenant peuvent être associées au même apprenant.
     * </p>
     * <p>
     * Cette relation est obligatoire : une sessionApprenant doit avoir un apprenant de renseigné.
     * La récupération de l'apprenant est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPRENANT_ID", nullable = false)
    @NotNull(message = "{sessionApprenant.apprenant.notnull}")
    private Apprenant apprenant;

    /**
     * Session de formation qui est rattachée à la sessionApprenant.
     * <p>
     * Association Many-to-One vers l'entité {@link SessionFormation}.
     * Permet de centraliser les informations liées à la session de formation et d'assurer l'intégrité des données.
     * Plusieurs SessionApprenant peuvent être associées à la même Session de formation.
     * </p>
     * <p>
     * Cette relation est obligatoire : une sessionApprenant doit avoir une session de formation de renseignée.
     * La récupération de la session de formation est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_FORMATION_ID", nullable = false)
    @NotNull(message = "{sessionApprenant.sessionFormation.notnull}")
    private SessionFormation sessionFormation;


}
