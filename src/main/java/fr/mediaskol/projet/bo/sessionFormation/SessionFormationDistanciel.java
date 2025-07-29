package fr.mediaskol.projet.bo.sessionFormation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Représente la session de formation en distanciel (FOAD) dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation en distanciel, telles que
 * l'identifiant, le contrat, le nombre de blocs, la date de relance auprès des apprenants, le commentaire en lien
 * avec la relance et le statut de la session à distance.
 * Il permet aux salariés de visualiser l'état d'avancement d'une session de formation en distanciel.
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
@Table(name="SESSION_FOAD")
public class SessionFormationDistanciel extends  SessionFormation {


//    /**
//     * Identifiant unique de la session de formation en distanciel.
//     * <p>
//     * Clé primaire générée automatiquement par la base de données (IDENTITY).
//     * </p>
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "SESSION_FOAD_ID")
//    private Long idSessionFormationDistanciel;

//    /**
//     * Numéro AF (Yoda) unique de la session de formation.
//     * <p>
//     * Ce numéro n'est pas obligatoire, car à la création d'une session de formation pour l'année suivante, il
//     * n'existe pas encore de numéro attribué par Ipéria.
//     * Il est limité à 30 caractères.
//     * </p>
//     */
//    @Column(name = "NO_AF_YODA", unique = true, length = 30, nullable = true)
//    private String noYodaFoad;


//    /**
//     * Libellé de la session de formation.
//     * <p>
//     * Formation en présentiel : composé du thème, des premières lettres du lieu de formation et de la date du premier
//     * jour de la session.
//     * Formation en distanciel : composé du thème, FOAD et date du premier jour de la session
//     * Ce champ est optionnel à la création de la session de formation et limité à 50 caractères.
//     * </p>
//     */
//    @Column(name = "LIBELLE_SESSION_FORMATION", length = 50)
//    private String libelleSessionFormation;


    /**
     * Commentaire en lien avec le contrat de la session de formation en distanciel
     * <p>
     * Ce champ est optionnel et limité à 300 caractères.
     * </p>
     */
    @Column(name = "CONTRAT_SESSION_FOAD", length = 300)
    private String contratSessionFormationDistanciel;

    /**
     * Nombre de blocs, en lien avec la certification ultérieure. Sur deux années, quand tous les blocs ont été
     * validés, une certification peut être mise en place.
     * <p>
     * Ce champ est optionnel.
     * </p>
     */
    @Column(name = "NB_BLOC_SESSION_FOAD")
    private Integer nbBlocSessionFormationDistanciel;

//    /**
//     * Date du début de la session de formation à distance.
//     * <p>
//     * Ce champ est obligatoire.
//     * </p>
//     */
//    @Column(name = "DATE_DEBUT_SESSION_FOAD", nullable = false)
//    @NotNull(message="{sessionFormationDistanciel.dateDebutSessionFoad.notnull}")
//    private LocalDate dateDebutSessionFormationDistanciel;

    /**
     * Date de la fin de la session de formation à distance.
     * <p>
     * Ce champ est obligatoire.
     * </p>
     */
    @Column(name = "DATE_FIN_SESSION_FOAD", nullable = false)
    @NotNull(message="{sessionFormationDistanciel.dateFinSessionFoad.notnull}")
    private LocalDate dateFinSessionFormationDistanciel;


    /**
     * Date de relance envers les apprenants pour s'assurer qu'ils suivent bien la session de formation en distanciel.
     * <p>
     * Ce champ est optionnel.
     * </p>
     */
    @Column(name = "DATE_RELANCE_SESSION_FOAD")
    private LocalDate dateRelanceSessionFormationDistanciel;


    /**
     * Commentaire en lien avec la relance envers les apprenants de la session de formation en distanciel.
     * <p>
     * Ce champ est optionnel et limité à 300 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_SESSION_FOAD", length = 2000)
    private String commentaireSessionFormationDistanciel;




}
