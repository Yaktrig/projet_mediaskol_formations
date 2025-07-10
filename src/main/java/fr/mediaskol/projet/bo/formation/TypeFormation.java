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
 * Représente un type de formation dans le système de gestion.
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
@Table(name = "TYPE_FORMATION")
public class TypeFormation {

    // Todo  test association

    /**
     * Identifiant unique du type de formation.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPE_FORMATION_ID")
    private Long idTypeFormation;

    /**
     * Libellé du type de formation.
     * <p>
     * Actuellement, il existe deux types de formations :
     * </p>
     * <ul>
     *  <li>Distanciel/li>
     *  <li>Présentiel</li>
     * </ul>
     * <p>
     * Ce champ est obligatoire, il est unique et limité à 100 caractères.
     * </p>
     */
    @Column(name = "LIBELLE_TYPE_FORMATION", nullable = false, unique = true, length = 100)
    @Size(min=5, max=100, message = "{typeFormation.libelle.size}")
    @NotNull(message = "{typeFormation.libelle.notnull}")
    @NotBlank(message = "{typeFormation.libelle.notblank}")
    private String libelleTypeFormation;
}
