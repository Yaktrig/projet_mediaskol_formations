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

/**
 * Représente un apprenant dans le système de gestion.
 * <p>
 * Cette entité hérite de {@link Personne} et contient les informations spécifiques à un apprenant,
 * telles que ses coordonnées, sa date de naissance, son statut, son passeport, son adresse et
 * les types de formations suivies.
 * </p>
 * <p>
 * Ajout du @Data de Lombok pour avoir les getter et les setter, toString, equals, hashCode
 * </p>
 *
 * @author Mélissa
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder // Héritage joined

@Entity
@Table(name = "APPRENANT")
public class Apprenant extends Personne {


    /**
     * Numéro de téléphone portable de l'apprenant
     */
    @Column(name = "NUM_PORTABLE_APPRENANT", nullable = true, length = 10)
    private String numPortable;

    /**
     * Date de naissance de l'apprenant
     * <p>
     * Ce champ est obligatoire et nécessaire
     * </p>
     */
    @Column(name = "DATE_NAISSANCE", nullable = false)
    private LocalDate dateNaissance;

    /**
     * Indique si l'apprenant est actuellement actif ou non
     */
    @Column(name = "STATUT_APPRENANT", nullable = false)
    private boolean statutApprenant;

    /**
     * Numéro du passeport unique de l'apprenant
     * <p>
     * Il n'est pas créé de suite, à la création de l'apprenant. Quand il sera créé, un livret avec le numéro sera
     * envoyé à l'apprenant.
     * </p>
     * <p>
     * Pour chaque session de formation suivie, l'apprenant pourra y accoler l'étiquette qui correspond à
     * l'intitulé de la formation. Ce livret sert à justifier auprès de son(ses) employeur(s) qu'il s'est formé.
     * </p>
     */
    @Column(name = "NUM_PASSEPORT", nullable = true, length = 120, unique = true)
    private String noPasseport;

    /**
     * Commentaire qui permet d'ajouter des informations sur l'apprenant en général
     */
    @Column(name = "COMMENTAIRE_APPRENANT", nullable = true, length = 2000)
    private String commentaireApprenant;

    /**
     * Adresse associée à l'apprenant
     * <p>
     * Association unidirectionnelle OneToOne avec {@link Adresse}
     * La suppression de l'apprenant entraîne la suppression de son adresse (cascade et orphanRemoval)
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESSE_ID")
    private Adresse adresse;

    /**
     * Liste des types de formations suivies par l'apprenant (Présentiel ou Distanciel actuellement)
     * <p>
     * Association ManyToMany avec {@link TypeFormation}
     * La suppression d'un apprenant ne supprime pas les types de formation
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TYPE_FORMATION_SUIVIE",    // Création de la table de jointure
            joinColumns = {@JoinColumn(name = "APPRENANT_ID")},// Clé étrangère qui pointe vers l'entité Apprenant
            inverseJoinColumns = {@JoinColumn(name = "TYPE_FORMATION_ID")}) // Seconde clé étrangère qui pointe vers l'entité TypeFormation
    @ToString.Exclude // quand j'affiche un apprenant, je n'affiche pas le ou les type(s) de formations qui y sont associé(s)
    private @Builder.Default List<TypeFormation> typeFormationSuivie = new ArrayList<>(); // Création d'une liste vide de type de formation (@Builder.Default)


    /**
     * Statut actuel du numéro de passeport de l'apprenant.
     * <p>
     * Ce champ indique l'état d'avancement de la gestion du numéro de passeport (créé, à créer, à envoyer).
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link EnumType#STRING}.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private StatutNumPasseport statutNumPasseport;
}
