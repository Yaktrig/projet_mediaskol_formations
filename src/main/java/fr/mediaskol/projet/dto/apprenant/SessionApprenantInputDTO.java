package fr.mediaskol.projet.dto.apprenant;

import fr.mediaskol.projet.bo.apprenant.StatutSessionApprenant;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionApprenantInputDTO {

    /**
     * Déclaration des attributs
     */
    private long idSessionApprenant;

    @Size(max = 2000, message = "{sessionApprenant.commentaireSessionApprenant.size}")
    private String commentaireSessionApprenant;

    @Size(min=3, max = 20, message = "{sessionApprenant.modeReceptionInscription.size}")
    @NotBlank(message = "{sessionApprenant.modeReceptionInscription.notblank}")
    private String modeReceptionInscription;

    private StatutSessionApprenant statutSessionApprenant;

    private ApprenantResponseDTO apprenant;

    private SessionFormationRespDTO sessionFormation;

}
