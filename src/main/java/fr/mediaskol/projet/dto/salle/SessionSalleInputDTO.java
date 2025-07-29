package fr.mediaskol.projet.dto.salle;

import fr.mediaskol.projet.bo.salle.StatutSessionSalle;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionSalleInputDTO {

    private Long idSessionSalle;

    @Size(max = 2000, message = "{sessionSalle.commentaire.size}")
    private String commentaireSessionSalle;

    private Float coutSessionSalle;

    private StatutSessionSalle statutSessionSalle;

    private SalleResponseDTO salle;

    private SessionFormationRespDTO sessionFormation;
}
