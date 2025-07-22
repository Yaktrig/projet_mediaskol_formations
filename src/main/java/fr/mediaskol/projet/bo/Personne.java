package fr.mediaskol.projet.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Représente une personne dans le système de gestion.
 * <p>
 * Cette entité contient les informations de base d'une personne,telles que ses nom, son prénom, et son adresse mail.
 * Elle sert de classe mère pour l'héritage des entités liées aux personnes (Salarié, Apprenant, Formateur)
 * </p>
 * <p>
 * Utilisation de Lombok :
 * <ul>
 *   <li>{@code @Data} : génère les getters, setters, toString, equals, hashCode</li>
 *   <li>{@code @SuperBuilder} : facilite la création d'objets pour les classes héritées</li>
 *   <li>{@code @NoArgsConstructor}, {@code @AllArgsConstructor} : constructeurs par défaut et complet</li>
 * </ul>
 * </p>
 *
 * @author Mélissa
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "PERSONNE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {


    /**
     * Identifiant unique de la personne.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSONNE_ID")
    private Long idPersonne;

    /**
     * Nom de la personne.
     * <p>
     * Ce champ est obligatoire et limité à 90 caractères.
     * </p>
     */
    @Column(name = "NOM", nullable = false, length = 90)
    @NotNull(message="{personne.nom.notnull}")
    private String nom;

    /**
     * Prénom de la personne.
     * <p>
     * Ce champ est obligatoire et limité à 150 caractères.
     * </p>
     */
    @Column(name = "PRENOM", nullable = false, length = 150)
    @NotNull(message="{personne.prenom.notnull}")
    private String prenom;

    /**
     * Adresse mail de la personne.
     * <p>
     * Ce champ est obligatoire, unique et limité à 255 caractères.
     * Il permet d'identifier une personne de façon fiable et d'assurer l'unicité dans le système.
     * </p>
     */
    @Column(name = "EMAIL", nullable = false, unique = true, length = 255)
    @NotNull(message = "{personne.email.notnull}")
    @Email
    private String email;

}
