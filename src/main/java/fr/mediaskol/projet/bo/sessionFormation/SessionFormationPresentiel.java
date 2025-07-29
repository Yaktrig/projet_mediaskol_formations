package fr.mediaskol.projet.bo.sessionFormation;

import fr.mediaskol.projet.bo.departement.Departement;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * Représente une session de formation (en présentiel, en distanciel) dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation,
 * telles que l'identifiant, le numéro AF (Yoda) unique, le libellé, le statut Yoda, le numéro du département concerné
 * pour le présentiel, la couleur associée au numéro du département et le statut métier de la session.
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
@ToString(callSuper=true)
@SuperBuilder

@Entity
@Table(name = "SESSION_FOP")
public class SessionFormationPresentiel extends  SessionFormation {


    /**
     * Ville du lieu principal de formation
     */
    @Column(name="LIEU", nullable=false, length=100)
    private String lieuSessionFormation;

    /**
     * Commanditaire de la session de formation
     * RPE (Relais Petite Enfance, Assistantes maternelles, Présidents d'association)
     */
    @Column(name= "COMMANDITAIRE", length=125)
    private String commanditaire;


    /**
     * RPE de la session de formation
     * Indique oui, si la date a été confirmée au RPE
     */
    @Column(name= "CONFIRMATION_RPE", length=255)
    private String confirmationRPE;


    /**
     * Département auquel est rattachée la session de formation (en présentiel).
     * <p>
     * Association Many-to-One vers l'entité {@link Departement}.
     * Permet de centraliser les informations liées au département (numéro, nom, région, couleur, etc.)
     * et d'assurer l'intégrité des données. Plusieurs Sessions peuvent être associées au même département.
     * </p>
     * <p>
     * Cette relation est facultative : une session de formation en distanciel n'a pas de numéro de département renseigné.
     * La récupération du département est effectuée en mode paresseux (lazy loading).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;




}