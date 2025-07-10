package fr.mediaskol.projet.bo.NoteDeFrais;

import fr.mediaskol.projet.bo.formateur.Formateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Représente une note de frais émise par un formateur salarié dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une note de frais, elle contient un identifiant,
 *  l'intitulé de la session de formation concernée, la date de la session de formation concernée, le nombre de
 *  kilomètres, le prix du repas, le prix pour la pause café, les frais divers, le prix de la nuit d'hôtel, le montant
 *  total.
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
@Table(name="NOTE_DE_FRAIS")
public class NoteDeFrais {


    // Todo tester association à Formateur + message validation


    /**
     * Identifiant unique de la note de frais
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="NOTE_DE_FRAIS_ID")
    private int idNoteDeFrais;

    // Todo libelle note de frais ??

    /**
     * Libellé de la session de formation concernée par la note de frais
     * <p>
     * Ce champ est obligatoire à la création de la note de frais et limité à 120 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_SESSION_FORMATION", nullable = false, length = 120)
    @Size(min=3, max = 120)
    @NotNull
    @NotBlank
    private String libelleSessionFormation  ;

    // Todo dates pertinentes ? Date session - date création - date règlement
    /**
     * Date de la session de formation
     * <p>
     * Ce champ est obligatoire à la création de la note de frais.
     * </p>
     */
    @Column(name = "DATE_SESSION_FORMATION", nullable = false)
    @NotNull
    private LocalDate dateSessionFormation;

    /**
     * Date de l'enregistrement de la note de frais
     * <p>
     * Ce champ est obligatoire à la création de la note de frais.
     * </p>
     */
    @Column(name = "DATE_NOTE_DE_FRAIS", nullable = false)
    @NotNull
    private LocalDate dateNoteDeFrais;

    /**
     * Date du règlement de la note de frais
     * <p>
     * Ce champ est obligatoire à la création de la note de frais.
     * </p>
     */
    @Column(name = "DATE_REGLEMENT_NOTE_DE_FRAIS", nullable = false)
    @NotNull
    private LocalDate dateReglementNoteDeFrais;


    /**
     * Date de l'enregistrement de la note de frais
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "NB_KM")
    private Float nbKm;


    /**
     * Prix du repas (maximum 12 € et sur justificatif
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "MONTANT_REPAS")
    @Max(12L)
    private Float montantRepas;

    /**
     * Montant pour la pause café (maximum 5 € par jour et sur justificatif)
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "MONTANT_PAUSE_CAFE")
    private Float montantPauseCafe;


    /**
     * Montant pour des frais divers (sur justificatif)
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "MONTANT_FRAIS_DIVERS")
    private Float montantFraisDivers;

    /**
     * Libellé pour les frais divers.
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "LIBELLE_FRAIS_DIVERS")
    private String libelleFraisDivers;


    /**
     * Montant nuit d'hôtel
     * <p>
     * Ce champ est optionnel à la création de la note de frais.
     * </p>
     */
    @Column(name = "MONTANT_NUIT_HOTEL")
    private Float montantNuitHotel;

    /**
     * Montant total de la note de frais
     * <p>
     * Ce champ est obligatoire à la création de la note de frais.
     * </p>
     */
    @Column(name = "MONTANT_TOTAL_NOTE_DE_FRAIS")
    @NotNull
    private Float montantTotalNoteDeFrais;

    /**
     * Formateur (salarié) qui a émis cette note de frais.
     * <p>
     * Association Many-to-One vers l'entité {@link Formateur}.
     * Une note de frais peut être émise par un formateur, auquel cas, elle est rattachée à celui-ci.
     * </p>
     * <p>
     * Cette relation est obligatoire : une note de frais concerne forcément un formateur.
     * Le chargement du formateur est effectué en mode paresseux (lazy loading) pour optimiser les performances.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMATEUR_ID", nullable = false)
    private Formateur formateur;

}
