package fr.mediaskol.projet.bo.document;

import fr.mediaskol.projet.bo.Personne;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Représente la catégorie d'un document dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une catégorie de document, tels que son identifiant et le
 * libellé.
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
@SuperBuilder // Héritage joined

@Entity
@Table(name="CATEGORIE_DOCUMENT")
public class CategorieDocument {


    /**
     * Identifiant unique de la catégorie de document.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORIE_DOCUMENT_ID")
    private Long idCategorieDocument;

    /**
     * Libellé de la catégorie d'un document (Agrément, Bulletin d'inscription, Carte ID, bulletin salaire de moins
     * de 3 mois, Carte SST.
     * <p>
     * Ce champ est obligatoire à la création d'une catégorie de document et limité à 120 caractères
     * </p>
     */
    @Column(name = "LIBELLE_CATEGORIE_DOCUMENT", length = 120)
    @Size(min = 2, max = 120, message = "{categorieDocument.libelle.size}")
    @NotNull(message = "{categorieDocument.libelle.notnull}")
    @NotBlank(message = "{categorieDocument.libelle.notblank}")
    private String libelleCategorieDocument;

    // Todo diminutif en back ou en front ?
}
