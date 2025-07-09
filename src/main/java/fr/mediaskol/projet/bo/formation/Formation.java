package fr.mediaskol.projet.bo.formation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une formation dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation en présentiel,
 * telles que l'identifiant, le thème et le libellé.
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
@Table(name="FORMATION")
public class Formation {

    // Todo message validations + vérifier si toutes les associations ok + test association ?

    /**
     * Identifiant unique de la formation.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORMATION_ID")
    private Long idFormation;


    /**
     * Thème de la formation.
     * <p>
     * Ce champ est obligatoire à la création de la formation, il est unique et limité à 20 caractères.
     * </p>
     */
    @Column(name = "THEME_FORMATION", nullable = false, unique=true, length = 20)
    @Size(min=3, max = 20)
    @NotNull
    @NotBlank
    private String themeFormation;


    /**
     * Libellé de la formation.
     * <p>
     * Ce champ est obligatoire à la création de la formation, il est unique et limité à 300 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_FORMATION", nullable = false, length = 300)
    @Size(min=3, max = 300)
    @NotNull
    @NotBlank
    private String libelleFormation;



}
