package fr.mediaskol.projet.bo.apprenant;

import fr.mediaskol.projet.bo.Personne;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Table(name="APPRENANT")
public class Apprenant extends Personne {

    // Attributs - Attributes

    // Numéro de portable de l'apprenant
    @Column(name="NUM_PORTABLE_APPRENANT", nullable = true, length = 10)
    private String numPortable;

    // Colonne non nulle - Non-zero column
    @Column(name="DATE_NAISSANCE", nullable = false)
    private LocalDate dateNaissance;

    // Si l'apprenant est actif ou non
    @Column(name="STATUT_APPRENANT", nullable = false)
    private boolean statutApprenant;

    // Numéro de passeport unique et propre à chaque apprenant
    @Column(name="NUM_PASSEPORT", nullable = true, length = 120, unique = true)
    private String noPasseport;

    // Commentaire qui indique diverses informations sur l'apprenant
    @Column(name="COMMENTAIRE_APPRENANT", nullable = true, length = 2000)
    private String commentaireApprenant;

    // Ajout de l'attribut adresse pour gérer l'association OneToOne
    // Association unidirectionnelle
    // Contrainte de composition - Les 2 entités ont le même cycle de vie (CascadeType.ALL)
    // Il faut que l'adresse devienne orpheline (orphanRemoval) et donc soit supprimée
    // Par défaut les 2 entités sont chargées
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="ADDRESSE_ID")
    private Adresse adresse;

    // ManyToMany entre Apprenant et TypeFormation
    // pas de cascade, car quand on supprime un apprenant, on ne supprime pas le type de formation
    @ManyToMany(fetch = FetchType.LAZY)
    // Création de la table de jointure
    @JoinTable(name = "TYPE_FORMATION_SUIVIE",
            // Clé étrangère qui pointe vers l'entité Apprenant
            joinColumns = {@JoinColumn(name = "APPRENANT_ID")},
            // Seconde clé étrangère qui pointe vers l'entité TypeFormation
            inverseJoinColumns = {@JoinColumn(name = "TYPE_FORMATION_ID")})
    // quand j'affiche un apprenant, je n'affiche pas le ou les type(s) de formations qui y sont associé(s)
    @ToString.Exclude
    // Création d'une liste vide de type de formation (@Builder.Default)
    private @Builder.Default List<TypeFormation> typeFormationSuivie = new ArrayList<>();

}
