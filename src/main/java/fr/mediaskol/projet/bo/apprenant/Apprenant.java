package fr.mediaskol.projet.bo.apprenant;

import fr.mediaskol.projet.bo.Personne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

// Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
// Add Lombok's @Data to have getter and setter, toString, equals, hashCode for all fields.

// Génère un constructeur sans argument - Generates a constructor with no arguments.
@NoArgsConstructor
// Génère un constructeur avec un argument pour chaque champ - Generates a constructor with an argument for each field.
@AllArgsConstructor
@Getter
@Setter
// Pour avoir les données de l'Employé au ajoute le callsuper
@ToString(callSuper = true)
// Héritage Joined - Makes it easy to implement the Builder design pattern
@SuperBuilder

// Cette classe est une entité JPA persistée en base de données
// Nom de la table associée à cette entité dans la base de données
// Déclaration de la classe de base pour toutes les personnes du système
@Entity
@Table(name="APPRENANT")
public class Apprenant extends Personne {

    // Attributs - Attributes

    // Numéro de portable de l'apprenant
    @Column(nullable = false, length = 10)
    private String numPortable;

    // Colonne non nulle - Non-zero column
    @Column(nullable = false)
    private LocalDate dateNaissance;

    // Si l'apprenant est actif ou non
    @Column(nullable = false)
    private boolean statutApprenant;

    // Numéro de passeport unique et propre à chaque apprenant
    @Column(nullable = true, length = 50, unique = true)
    private String noPasseport;

    // Commentaire qui indique diverses informations sur l'apprenant
    @Column(nullable = true, length = 2000)
    private String commentaireApprenant;


}
