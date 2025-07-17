package fr.mediaskol.projet.bo.sessionFormation;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Représente le traitement d'une session de formation terminée (en présentiel, en distanciel) dans le système
 * de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation terminée, telles que l'identifiant,
 * le statut de fin de formation Yoda, la date limite Yoda, le commentaire de fin de formation et le statut de fin de
 * formation.
 * Il permet aux salariés de visualiser l'état d'avancement du traitement lié à la fin d'une session de formation.
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
@Table(name = "FIN_SESSION_FORMATION")
public class FinSessionFormation {

    // Todo  voir les champs obligatoires

    /**
     * Identifiant unique de la fin de session de formation.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIN_SESSION_FORMATION_ID")
    private Long idFinSessionFormation;

    /**
     * Statut de la fin de formation - Permet de visualiser si la session de formation est totalement terminée.
     * <ul>
     *     <li>FF : Fin de formation</li>
     *     <li>T : validé par Mediaskol et Iperia </li>
     * </ul>
     * <p>
     * Ce champ n'est pas obligatoire.
     * </p>
     */
    @Column(name = "STATUT_YODA_FIN_SESSION_FORMATION", length = 5)
    @Size(max = 5, message="{sessionFinFormation.statutYoda.size}")
    private String statutYodaFinSessionFormation;


    /**
     * Date limite Yoda de la fin de session de formation
     * <p>
     * Ce champ n'est pas obligatoire.
     * </p>
     */
    @Column(name = "DATE_LIMITE_YODA_FIN_SESSION_FORMATION")
    private LocalDate dateLimiteYodaFinSessionFormation;

    /**
     * Commentaire en lien avec le traitement de la fin de la session de formation.
     * Permet au salarié d'indiquer ce qu'ils ont fait ou ce qui ne convient pas, quand Ipéria considère qu'il manque
     * des documents apprenants, qu'ils sont non conforme...
     * <p>
     * Ce champ n'est pas obligatoire.
     * </p>
     */
    @Column(name = "COMMENTAIRE_FIN_SESSION_FORMATION", length = 2000)
    @Size(max=2000, message="{sessionFinFormation.commentaire.size}")
    private String commentaireFinSessionFormation;


    /**
     * Statut métier de la fin d'une session de formation.
     * <p>
     * Ce champ indique l'état de la fin d'une session de formation. Le salarié pourra visualiser grâce à un code
     * couleur ce qu'il en est du traitement d'une session de formation quand elle est terminée.
     * <ul>
     *     <li></li>
     * </ul>
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutFinSessionFormation}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_FIN_SESSION_FORMATION")
    private StatutFinSessionFormation statutFinSessionFormation;



}
