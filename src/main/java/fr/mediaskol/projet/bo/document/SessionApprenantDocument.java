package fr.mediaskol.projet.bo.document;

import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.formation.Formation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Représente les documents pour l'inscription à une session de formation, envoyés par l'apprenant,
 * dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques au document nécessaire, telles que son identifiant, son nom,
 * la date de prise en compte (de réception), le chemin dans lequel le fichier est stocké et le statut (conforme,
 * non reçu, non conforme...).
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
@Table(name = "SESSION_APPRENANT_DOCUMENT")
public class SessionApprenantDocument {


    // Todo Test associations
    /**
     * Identifiant unique du document de l'apprenant pendant la session.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_APPRENANT_DOCUMENT_ID")
    private Long idSessionApprenantDocument;

    /**
     * Nom du document.
     * <p>
     * Ce champ est obligatoire et est limité à 200 caractères.
     * </p>
     */
    @Column(name = "NOM_SESSION_APPRENANT_DOCUMENT", length = 200)
    @Size(min=2, max = 200, message="{sessionApprenantDocument.nom.size}")
    @NotNull(message = "{sessionApprenantDocument.nom.notnull}")
    @NotBlank(message = "{sessionApprenantDocument.nom.notblank}")
    private String nomSessionApprenantDocument;

    /**
     * Date de prise en compte du document.
     * <p>
     * Ce champ n'est pas obligatoire.
     * </p>
     */
    @Column(name = "DATE_SESSION_APPRENANT_DOCUMENT")
    private LocalDate dateSessionApprenantDocument;

    /**
     * Chemin dans lequel le document est stocké.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "FICHIER_SESSION_APPRENANT_DOCUMENT", length = 2000)
    @Size(max = 2000, message = "{sessionApprenantDocument.fichier.size}")
//    @NotNull
//    @NotBlank
    private String fichierSessionApprenantDocument;

    /**
     * Statut métier de SessionApprenantDocument.
     * <p>
     * Ce champ indique l'état du document remis par l'apprenant pour suivre la session de formation (conforme, non
     * conforme, non reçu...)
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionApprenantDocument}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_SESSION_APPRENANT_DOCUMENT")
    private StatutSessionApprenantDocument statutSessionApprenantDocument;



    /**
     * Session d’inscription (SessionApprenant) à laquelle ce document est rattaché.
     * <p>
     * Association Many-to-One vers l’entité {@link SessionApprenant}.
     * Chaque document d’un apprenant est obligatoirement lié à une session d’inscription spécifique, ce qui permet
     * de regrouper et de centraliser tous les documents nécessaires à la gestion administrative de la participation
     * à une session de formation. Plusieurs documents peuvent ainsi être associés à une même inscription,
     * facilitant le suivi et la vérification des pièces fournies.
     * </p>
     * <p>
     * Cette relation est obligatoire : un document ne peut exister sans être rattaché à une session d’inscription
     * précise, garantissant ainsi l’intégrité des données et la traçabilité des justificatifs pour chaque apprenant.
     * Le chargement de la session d’inscription est effectué en mode paresseux (lazy loading) afin d’optimiser les
     * performances lors de l’accès aux documents sans charger systématiquement toutes les informations de la session.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_APPRENANT_ID", nullable = false)
    @NotNull(message = "{sessionApprenantDocument.sessionApprenant.notnull}")
    private SessionApprenant sessionApprenant;


    /**
     * Categorie à laquelle appartient ce document d'inscription.
     * <p>
     * Association Many-to-One vers l'entité {@link CategorieDocument}.
     * Chaque document fourni par l’apprenant est obligatoirement rattaché à une catégorie (ex : carte d’identité,
     * agrément…), ce qui permet de typer le document, d’automatiser les contrôles et de faciliter la gestion des
     * pièces requises pour chaque session.
     * </p>
     * <p>
     * Cette relation est obligatoire : elle garantit qu’aucun document n’est stocké sans catégorie, assurant
     * ainsi la cohérence métier et la traçabilité des justificatifs attendus pour l’inscription.
     * Le chargement de la catégorie est effectué en mode paresseux (lazy loading) pour optimiser les performances.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIE_DOCUMENT_ID", nullable = false)
    @NotNull(message = "{sessionApprenantDocument.categorieDocument.notnull}")
    private CategorieDocument categorieDocument;
}
