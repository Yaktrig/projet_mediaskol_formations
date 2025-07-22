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
     * Ce champ est obligatoire à la création de la formation, il est limité à 20 caractères.
     * </p>
     */
    @Column(name = "THEME_FORMATION", nullable = false, length = 20)
    @Size(min=3, max = 20, message = "{formation.themeFormation.size}")
    @NotNull(message = "{formation.themeFormation.notnull}")
    @NotBlank(message = "{formation.themeFormation.notblank}")
    private String themeFormation;


    /**
     * Libellé de la formation.
     * <p>
     * Ce champ est obligatoire à la création de la formation, il est unique et limité à 300 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_FORMATION", nullable = false, length = 300)
    @Size(min=3, max = 300, message = "{formation.libelleFormation.size}")
    @NotNull(message = "{formation.libelleFormation.notnull}")
    @NotBlank(message = "{formation.libelleFormation.notblank}")
    private String libelleFormation;


    /**
     * Type de formation qui est rattaché à la formation.
     * <p>
     * Association Many-to-One vers l'entité {@link TypeFormation}.
     * Permet de centraliser les informations liées au type de formation (présentiel ou distanciel)
     * et d'assurer l'intégrité des données. Plusieurs formations peuvent être associées au même type de formation.
     * </p>
     * <p>
     * Cette relation est obligatoire : une formation doit avoir un type de formation renseigné.
     * La récupération du type de formation est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_FORMATION_ID", nullable = false)
    @NotNull(message = "{formation.typeFormation.notnull}")
    private TypeFormation typeFormation;


}
