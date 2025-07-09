package fr.mediaskol.projet.bo.departement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un département dans le système de gestion.
 * <p>
 * Cette entité contient les informations de base d'un département, telles que son numéro, son nom, sa région,
 * sa couleur hexadécimale.
 * * </p>
 * <p>
 * Pour le moment, nous avons besoin de colorer que les départements de la Bretagne, mais dans le futur,
 * cette application pourrait être amenée à être utilisée dans toute la France (ou besoin pour le distanciel).
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
@Table(name = "DEPARTEMENT")
public class Departement {

    // Todo message validation

    /**
     * Identifiant unique du département.
     */
    @Id
    @Column(name = "DEPARTEMENT_ID")
    private Long idDepartement;

    /**
     * Nom du département
     * <p>
     * Ce champ est obligatoire, unique et limité à 100 caractères.
     * </p>
     */
    @Column(name = "NOM_DEPARTEMENT", unique = true, nullable = false, length = 100)
    @Size(min=3, max = 100)
    @NotNull
    @NotBlank
    private String nomDepartement;

    /**
     * Région
     * <p>
     * Ce champ est obligatoire et limité à 100 caractères.
     * </p>
     */
    @Column(name = "REGION", nullable = false, length = 100)
    @Size(min=3, max = 100)
    @NotNull
    @NotBlank
    private String region;

    /**
     * Couleur hexadécimale du département
     * <p>
     * Ce champ est obligatoire et limité à 7 caractères.
     * </p>
     */
    @Column(name = "COULEUR_DEPARTEMENT", nullable = false, length = 7)
    @Size(min=3, max = 7)
    @NotNull
    @NotBlank
    private String couleurDepartement;
}
