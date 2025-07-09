package fr.mediaskol.projet.bo.salle;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une session salle (en présentiel) dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation,
 * telles que l'identifiant, un commentaire propre au moment de la session, le coût négocié de la salle,
 * le statutSessionSalle qui permet de visualiser son statut.
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
@Table(name = "SESSION_SALLE")
public class SessionSalle {

    /**
     * Identifiant unique de la session salle.
     * <p>
     * Clé primaire générée automatiquement par la base de données (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_SALLE_ID")
    private Long idSessionSalle;

    /**
     * Commentaire général à propos de la salle durant la session.
     * <p>
     * Ce champ n'est pas obligatoire et est limité à 2000 caractères.
     * </p>
     */
    @Column(name = "COMMENTAIRE_SESSION_SALLE", length = 2000)
    @Size(max = 2000)
    private String commentaireSessionSalle;


    /**
     * Coût de la salle durant la session.
     * <p>
     * Ce champ n'est pas obligatoire.
     * </p>
     */
    @Column(name = "COUT_SESSION_SALLE")
    private Float coutSessionSalle;

    /**
     * Statut métier de la session salle.
     * <p>
     * Ce champ indique l'état de la session salle
     * La valeur est stockée en base sous forme de chaîne de caractères grâce à {@link StatutSessionSalle}
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_SESSION_SALLE")
    private StatutSessionSalle statutSessionSalle;
}
