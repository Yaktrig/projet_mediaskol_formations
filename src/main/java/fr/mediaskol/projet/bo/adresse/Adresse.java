package fr.mediaskol.projet.bo.adresse;

import fr.mediaskol.projet.bo.departement.Departement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Représente une adresse dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une adresse pour les formateurs, les salles et les apprenants.
 * Telles que l'identifiant, le numéro et le nom de la rue, le code postal, la ville.
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
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude="departement")
@Builder

@Entity
@Table(name="ADRESSE")
public class Adresse {



    /**
     * Identifiant unique de l'adresse.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADRESSE_ID")
    private long idAdresse;

    /**
     * Numéro et nom de la rue.
     * <p>
     * Ce champ est optionnel et limité à 50 caractères.
     * </p>
     */
    @Column(name = "RUE", length = 250)
    @Size(max = 250, message = "{adresse.rue.size}")
    private String rue;

    /**
     * Numéro du code postal.
     * <p>
     * Ce champ est optionnel et limité à 5 caractères.
     * </p>
     */
    @Column(name = "CODE_POSTAL", length = 5)
    @Size(max = 5, message = "{adresse.codePostal.size}")
    private String codePostal;

    /**
     * Nom de la ville.
     * <p>
     * Ce champ est optionnel et limité à 200 caractères.
     * </p>
     */
    @Column(name = "VILLE", length = 200)
    @Size(max = 200, message ="{adresse.ville.size}" )
    private String ville;


    /**
     * Département auquel est rattachée l'adresse.
     * <p>
     * Association Many-to-One vers l'entité {@link Departement}.
     * Permet de centraliser les informations liées au département (numéro, nom, région, couleur, etc.)
     * et d'assurer l'intégrité des données. Plusieurs adresses peuvent être associées au même département.
     * </p>
     * <p>
     * Cette relation est facultative : une adresse n'est pas obligée d'avoir un numéro de département renseigné.
     * La récupération du département est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUM_DEPARTEMENT", nullable = false)
    //@NotNull(message = "{adresse.departement.notnull}")
    private Departement departement;

}
