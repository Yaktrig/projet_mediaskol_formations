package fr.mediaskol.projet.bo.salle;


import fr.mediaskol.projet.bo.adresse.Adresse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * Représente une Salle dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une salle, où se déroulent les formations,
 * telles que l'identifiant, le nom de la salle, le nom du contact, le numéro de portable du contact, l'adresse mail
 * du contact, s'il y a une clé ou non, le digicode s'il y en a un, le commentaire général.
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
@Table(name = "SALLE")
public class Salle {


    /**
     * Identifiant unique de la salle.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALLE_ID")
    private Long idSalle;

    /**
     * Nom de la salle.
     * <p>
     * Ce champ est obligatoire à la création de la formation et limité à 255 caractères.
     * </p>
     */
    @Column(name = "NOM_SALLE", nullable = false, length = 255)
    @NotNull(message = "{salle.nom.notnull}")
    private String nomSalle;

    /**
     * Nom et prénom du contact de la salle.
     * <p>
     * Ce champ n'est pas obligatoire et il est limité à 200 caractères.
     * </p>
     */
    @Column(name = "NOM_CONTACT", length = 200)
    private String nomContact;

    /**
     * Numéro de téléphone du contact de la salle.
     * <p>
     * Ce champ n'est pas obligatoire et il est limité à 10 caractères.
     * </p>
     */
    @Column(name = "PORTABLE_CONTACT", length = 10)
    private String portableContact;

    /**
     * Adresse mail du contact de la salle.
     * <p>
     * Ce champ n'est pas obligatoire et il est limité à 255 caractères.
     * </p>
     */
    @Column(name = "MAIL_CONTACT", length = 255)
    private String mailContact;

    /**
     * Indique s'il y a besoin d'une clé pour accéder à la salle.
     * <ul>
     *     <li> 0 : pas de clé </li>
     *     <li> 1 : besoin d'une clé</li>
     * </ul>
     */
    @Column(name = "CLE_SALLE", nullable = false)
    @Builder.Default
    private Boolean cleSalle = false;

    /**
     * Numéro du digicode s'il y en a un pour accéder à la salle.
     * <p>
     * Ce champ n'est pas obligatoire et limité à 10 caractères
     * </p>
     */
    @Column(name = "DIGICODE_SALLE",  length = 10)
    private String digicodeSalle;

    /**
     * Commentaire général à propos de la salle.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_SALLE",  length = 2000)
    private String commentaireSalle;




    /**
     * Adresse associée à la salle.
     * <p>
     * Association unidirectionnelle One-to-One vers l'entité {@link Adresse}.
     * <ul>
     *   <li>La composition est assurée : la salle et son adresse partagent le même cycle de vie.</li>
     *   <li>La suppression d'une salle entraîne automatiquement la suppression de son adresse (cascade et orphanRemoval).</li>
     *   <li>Le chargement de l'adresse est immédiat (EAGER).</li>
     * </ul>
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADRESSE_ID")
    private Adresse adresse;
}
