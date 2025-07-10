package fr.mediaskol.projet.bo.sessionFormationDistanciel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente la session de formation en distanciel dans le système de gestion.
 * <p>
 * Cette entité contient les informations spécifiques à une session de formation en distanciel, telles que
 * l'identifiant, le contrat, la relance, le commentaire et le statut de la session.
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
@Data
@Builder

@Entity
@Table(name="SESSION_FORMATION_DISTANCIEL")
public class SessionFormationDistanciel {

    // Todo message validations + vérifier si toutes les associations ok + test association ?
    // Todo créer la bo


/**
 * -idSessionFoad : long
 * -contratSessionFoad : String
 * -relanceSessionFoad : String ou LocalDate
 * -commentaireSessionFoad : String
 * -statutSessionFOAD: Enum
 */
}
