package fr.mediaskol.projet.bo;

import lombok.*;

// Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
// Add Lombok's @Data to have getter and setter, toString, equals, hashCode for all fields.
@Data
// Génère un constructeur sans argument.
// Generates a constructor with no arguments.
@NoArgsConstructor
// Génère un constructeur avec un argument pour chaque champ.
// Generates a constructor with an argument for each field.
@AllArgsConstructor
// Génère un constructeur sans argument.
// Generates a constructor with no arguments.
@Builder

/** @Mélissa
 * Classe Business Object BO
 * Respecte le design pattern POJO (Plained Old Java Object)
 * Définie avec Lombok
 * Classe mère des entités Salarie, Formateur, Apprenant

 * Business Object BO class
 * Respects the POJO (Plained Old Java Object) design pattern
 * Defined with Lombok
 * Parent class for Salarie, Formateur and Apprenant entities
 */
public class Personne {

    // Attributs
    // Attributes
    private Integer idPersonne;

    private String nom;

    private String prenom;

    private String email;

}
