package fr.mediaskol.projet.bo.apprenant;

import fr.mediaskol.projet.bo.Personne;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
     * <p>
     * Ce champ n'est pas obligatoire et limité à 10 caractères
     * </p>
     */
    @Column(name = "NUM_PORTABLE_APPRENANT", length = 10)
    @Size(max = 10, message = "{apprenant.numPortable.size}")
    private String numPortable;

    /**
     * Date de naissance de l'apprenant
     * <p>
     * Ce champ est obligatoire et nécessaire
     * </p>
     */
    @Column(name = "DATE_NAISSANCE", nullable = false)
    @NotNull(message = "{apprenant.dateNaissance.notnull}")
    private LocalDate dateNaissance;

    /**
     * Indique si l'apprenant est actif.
     * <p>
     * Ce champ est toujours renseigné : true (actif) ou false (inactif).
     * </p>
     */
    @Column(name = "APPRENANT_ACTIF", nullable = false)
    @Builder.Default
    private boolean apprenantActif = true;

    /**
     * Numéro du passeport unique de l'apprenant
     * <ul>
     *      <li>Il n'est pas créé de suite, à la création de l'apprenant. Quand il sera créé, un livret avec le numéro sera
     *          envoyé à l'apprenant.
     *      </li>
     *      <li>Pour chaque session de formation suivie, l'apprenant pourra y accoler l'étiquette qui correspond à
     *          l'intitulé de la formation. Ce livret sert à justifier auprès de son(ses) employeur(s) qu'il s'est
     *          formé.
     *      </li>
     * </ul>
     */
    @Column(name = "NUM_PASSEPORT", length = 120, unique = true)
    @Size(max = 120, message = "{apprenant.numPasseport.size}")
    private String numPasseport;

    /**
     * Statut actuel du numéro de passeport de l'apprenant.
     * <p>
     * Ce champ indique l'état d'avancement de la gestion du numéro de passeport (créé, à créer, à envoyer).
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link EnumType#STRING}.
     * </p>
     * <p>
     * Le statut toujours obligatoire
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_NUM_PASSEPORT", nullable = false)
    @NotNull(message = "{apprenant.statutNumPasseport.notnull}")
    private StatutNumPasseport statutNumPasseport;

    /**
     * Commentaire qui permet d'ajouter des informations sur l'apprenant en général
     */
    @Column(name = "COMMENTAIRE_APPRENANT", length = 2000)
    @Size(max = 2000, message = "{apprenant.commentaireApprenant.size}")
    private String commentaireApprenant;

    /**
     * Adresse associée à l'apprenant
     * <ul>
     *  <li>Association unidirectionnelle OneToOne avec {@link Adresse}</li>
     *  <li>La suppression de l'apprenant entraîne la suppression de son adresse (cascade et orphanRemoval)</li>
     *  <li>Optionnel, on n'oblige pas à créer l'adresse du formateur, lors de sa création, peut être fait par la suite</li>
     * </ul>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADRESSE_ID")
    private Adresse adresse;

    /**
     * Liste des types de formations suivies par l'apprenant (Présentiel ou Distanciel actuellement)
     * <p>
     * Association Many-to-Many avec l'entité {@link TypeFormation} via la table de jointure <b>TYPE_FORMATION_SUIVIE</b>.
     *  <ul>
     *      <li>Aucune cascade : la suppression d'un apprenant ne supprime pas les types de formation existants.</li>
     *      <li>Le chargement est paresseux (LAZY) pour optimiser les performances.</li>
     *      <li>La liste est initialisée vide par défaut.</li>
     *      <li>Exclue du toString pour éviter les affichages volumineux.</li>
     *  </ul>
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TYPE_FORMATION_SUIVIE",
            joinColumns = {@JoinColumn(name = "APPRENANT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TYPE_FORMATION_ID")})
    @ToString.Exclude
    private @Builder.Default Set<TypeFormation> typeFormationSuivie = new HashSet<>();

    // TODO vérifier si le type de formation suivie ok pour apprenant


}
