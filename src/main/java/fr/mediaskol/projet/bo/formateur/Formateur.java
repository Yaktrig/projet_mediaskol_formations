package fr.mediaskol.projet.bo.formateur;

import fr.mediaskol.projet.bo.Personne;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Représente un formateur dans le système de gestion.
 * <p>
 * Cette entité hérite de {@link Personne} et contient les informations spécifiques à un formateur,
 * telles que ses coordonnées, son numéro de portable, son statut, la zone où il intervient, le commentaire,
 * son adresse, les formations dispensées et le type de formations dispensées.
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
@SuperBuilder


@Entity
@Table(name = "FORMATEUR")
public class Formateur extends Personne {


    /**
     * Numéro de téléphone portable du formateur.
     * <p>
     * Ce champ n'est pas obligatoire et limité à 10 caractères.
     * </p>
     */
    @Column(name = "NUM_PORTABLE_FORMATEUR", length = 10)
    @Size(max = 10, message = "{formateur.numPortable.size}")
    private String numPortable;

    /**
     * Statut du formateur.
     * <p>
     * Ce champ est obligatoire et est compris entre 1 et 10 caractères.
     * </p>
     * Indique si le formateur est salarié ou auto-entrepreneur.
     *  <ul>
     *    <li>S : Salarié</li>
     *    <li>AE : Auto-entrepreneur</li>
     * </ul>
     */
    @Column(name = "STATUT_FORMATEUR", nullable = false, length = 10)
    @Size(min=1, max = 10, message = "{formateur.statutFormateur.size}")
    @NotNull(message = "{formateur.statutFormateur.notnull}")
    @NotBlank(message = "{formateur.statutFormateur.notblank}")
    private String statutFormateur;


    /**
     * Zone d'intervention du formateur pour une formation.
     * <p>
     * Ce champ n'est pas obligatoire, car il y a des formateurs qui animent seulement des formations en distanciel.
     * Ce champ est limité à 1000 caractères.
     * </p>
     */
    @Column(name = "ZONE_INTERVENTION",length = 1000)
    @Size(max = 1000, message = "{formateur.zoneIntervention.size}")
    private String zoneIntervention;

    /**
     * Commentaire général à propos du formateur.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_FORMATEUR",  length = 2000)
    @Size(max = 2000,message = "{formateur.commentaireFormateur.size}")
    private String commentaireFormateur;

    /**
     * Adresse associée au formateur.
     * <p>
     * Association unidirectionnelle One-to-One vers l'entité {@link Adresse}.
     * <ul>
     *   <li>La composition est assurée : le formateur et son adresse partagent le même cycle de vie.</li>
     *   <li>La suppression d'un formateur entraîne automatiquement la suppression de son adresse (cascade et orphanRemoval).</li>
     *   <li>Le chargement de l'adresse est immédiat (EAGER).</li>
     *   <li>Optionnel, on n'oblige pas à créer l'adresse du formateur, lors de sa création, peut être fait par la suite</li>
     * </ul>
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADRESSE_ID")
    private Adresse adresse;

    /**
     * Liste des formations dispensées par le formateur.
     * <p>
     * Association Many-to-Many avec l'entité {@link Formation} via la table de jointure <b>FORMATIONS_DISPENSEES</b>.
     * <ul>
     *   <li>Aucune cascade : la suppression d'un formateur ne supprime pas les formations existantes.</li>
     *   <li>Le chargement est paresseux (LAZY) pour optimiser les performances.</li>
     *   <li>La liste est initialisée vide par défaut.</li>
     *   <li>Exclue du toString pour éviter les affichages volumineux.</li>
     * </ul>
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FORMATIONS_DISPENSEES",
            joinColumns = {@JoinColumn(name = "FORMATEUR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FORMATION_ID")})
    @ToString.Exclude
    private @Builder.Default Set<Formation> formationsDispensees;

    /**
     * Liste des types de formation que le formateur est habilité à dispenser.
     * <p>
     *  Association Many-to-Many avec l'entité {@link TypeFormation} via la table de jointure <b>TYPE_FORMATION_DISPENSEE</b>.
     *  <ul>
     *      <li>Aucune cascade : la suppression d'un formateur ne supprime pas les types de formation existants.</li>
     *      <li>Le chargement est paresseux (LAZY) pour optimiser les performances.</li>
     *      <li>La liste est initialisée vide par défaut.</li>
     *      <li>Exclue du toString pour éviter les affichages volumineux.</li>
     * </ul>
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TYPE_FORMATION_DISPENSEE",
            joinColumns = {@JoinColumn(name = "FORMATEUR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TYPE_FORMATION_ID")})
    @ToString.Exclude
    private @Builder.Default List<TypeFormation> typeFormationDispensee = new ArrayList<>();

    // Todo vérifier dans le test pour le type de formation
}
