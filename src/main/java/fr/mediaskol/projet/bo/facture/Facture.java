package fr.mediaskol.projet.bo.facture;

import fr.mediaskol.projet.bo.document.CategorieDocument;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.salle.Salle;
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
 * Représente une facture émise par un formateur, une salle dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une facture, elle contient un identifiant, un numéro de facture,
 * un libellé, le montant, la date de la facture, la date du règlement, le chemin dans lequel est stocké le fichier.
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
@Table(name="FACTURE")
public class Facture {

    // Todo tester association à Formateur, Salle + message validation


    /**
     * Identifiant unique de la facture
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FACTURE_ID")
    private int idFacture;

    /**
     * Numéro de la facture
     * <p>
     * Ce champ est obligatoire à la création de la facture et limité à 50 caractères.
     * </p>
     */
    @Column(name = "NUM_FACTURE", nullable = false, length = 50)
    @Size(min=3, max = 50)
    @NotNull
    @NotBlank
    private String numFacture;

    /**
     * Libellé de la facture
     * <p>
     * Ce champ est obligatoire à la création de la facture et limité à 120 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_FACTURE", nullable = false, length = 120)
    @Size(min=3, max = 120)
    @NotNull
    @NotBlank
    private String libelleFacture;


    /**
     * Montant de la facture
     * <p>
     * Ce champ est obligatoire à la création de la facture.
     * </p>
     */
    @Column(name = "MONTANT_FACTURE", nullable = false)
    @NotNull
    private Float montantFacture;

    /**
     * Date de la facture
     * <p>
     * Ce champ est obligatoire à la création de la facture.
     * </p>
     */
    @Column(name = "DATE_FACTURE", nullable = false)
    @NotNull
    private LocalDate dateFacture;

    /**
     * Date du règlement de la facture
     * <p>
     * Ce champ est optionnel à la création de la facture.
     * </p>
     */
    @Column(name = "DATE_REGLEMENT_FACTURE")
    @NotNull
    private LocalDate dateReglementFacture;

    /**
     * Chemin du fichier de la facture.
     * <p>
     * Ce champ est optionnel à la création de la facture.
     * </p>
     */
    @Column(name = "FICHIER_FACTURE")
    @NotNull
    private LocalDate fichierFacture;


    /**
     * Formateur qui a émis cette facture.
     * <p>
     * Association Many-to-One vers l'entité {@link Formateur}.
     * Une facture peut être émise par un formateur, auquel cas, elle est rattachée à celui-ci.
     * </p>
     * <p>
     * Cette relation est optionnelle : une facture concernera soit un formateur, soit une salle, soit autre...
     * Le chargement du formateur est effectué en mode paresseux (lazy loading) pour optimiser les performances.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMATEUR_ID")
    private Formateur formateur;

    /**
     * Salle qui a émis cette facture.
     * <p>
     * Association Many-to-One vers l'entité {@link Salle}.
     * Une facture peut être émise par une salle, auquel cas, elle est rattachée à celle-ci.
     * </p>
     * <p>
     * Cette relation est optionnelle : une facture concernera soit un formateur, soit une salle, soit autre...
     * Le chargement de la salle est effectué en mode paresseux (lazy loading) pour optimiser les performances.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALLE_ID")
    private Salle salle;

}















