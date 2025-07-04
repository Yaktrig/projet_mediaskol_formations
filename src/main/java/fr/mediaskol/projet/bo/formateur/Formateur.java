package fr.mediaskol.projet.bo.formateur;

// Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
// Add Lombok's @Data to have getter and setter, toString, equals, hashCode for all fields.

import fr.mediaskol.projet.bo.Personne;
import fr.mediaskol.projet.bo.adresse.Adresse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Entity
@Table(name="FORMATEUR")
public class Formateur extends Personne {

    // Attributs - Attributes

    // Numéro de portable du formateur
    @Column(name="NUM_PORTABLE_FORMATEUR", nullable = true, length = 10)
    private String numPortable;

    // Si le formateur salarié (S) ou auto-entrepreneur (AE)
    @Column(name="STATUT_FORMATEUR", nullable = false)
    private String statutFormateur;

    // Zone d'intervention pour le formateur
    // peut être nulle si les formateurs ne font que du distanciel
    @Column(name="ZONE_INTERVENTION", nullable = true, length = 1000)
    private String zoneIntervention;

    // Commentaire qui indique diverses informations sur le formateur
    @Column(name="COMMENTAIRE_FORMATEUR", nullable = true, length = 2000)
    private String commentaireFormateur;

    // Ajout de l'attribut adresse pour gérer l'association OneToOne
    // Association unidirectionnelle
    // Contrainte de composition - Les 2 entités ont le même cycle de vie (CascadeType.ALL)
    // Il faut que l'adresse devienne orpheline (orphanRemoval) et donc soit supprimée
    // Par défaut les 2 entités sont chargées
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESSE_ID")
    private Adresse adresse;

}
