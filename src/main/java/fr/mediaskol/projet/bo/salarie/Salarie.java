package fr.mediaskol.projet.bo.salarie;

import fr.mediaskol.projet.bo.Personne;
import jakarta.persistence.*;
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
// Pour avoir les données de la Personne au ajoute le callsuper
@ToString(callSuper = true)
// Héritage Joined - Makes it easy to implement the Builder design pattern
@SuperBuilder

// Cette classe est une entité JPA persistée en base de données
// Nom de la table associée à cette entité dans la base de données
@Entity
@Table(name="SALARIE")
public class Salarie extends Personne {

    // Attributs - Attributes

    // Mot de passe du salarié
    // Peut être nul si son compte n'a pas encore été activé
    @Column(name="MOT_DE_PASSE", nullable = true, length = 68)
    private String mdp;

    // Permet au salarié de visualiser/distinguer qui traîte les dossiers et ses dossiers
    @Column(name="COULEUR_SALARIE", nullable = false, length = 20)
    private String couleurSalarie;

    // Permet de définir les droits sur l'application
    @Column(name="ROLE_SALARIE", nullable = false, length = 50)
    private String roleSalarie;

    // Si l'inscription du salarié est active ou non
    @Column(name="STATUT_INSCRIPTION", nullable = false)
    private boolean statutInscription;


}
