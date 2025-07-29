package fr.mediaskol.projet.dto.apprenant;

import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.apprenant.StatutSessionApprenant;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionApprenantRespDTO {

    /**
     * Déclaration des attributs
     */
    private long idSessionApprenant;
    private String commentaireSessionApprenant;
    private String modeReceptionInscription;
    private StatutSessionApprenant statutSessionApprenant;
    private ApprenantResponseDTO apprenant;
    private SessionFormationRespDTO sessionFormation;




    /**
     * Constructeur
     */
    public SessionApprenantRespDTO(SessionApprenant sessionApprenant){
        this.idSessionApprenant = sessionApprenant.getIdSessionApprenant();
        this.commentaireSessionApprenant = sessionApprenant.getCommentaireSessionApprenant();
        this.modeReceptionInscription = sessionApprenant.getModeReceptionInscription();
        this.statutSessionApprenant = sessionApprenant.getStatutSessionApprenant();
        this.apprenant = new ApprenantResponseDTO(sessionApprenant.getApprenant());
        this.sessionFormation = new SessionFormationRespDTO(sessionApprenant.getSessionFormation());

    }
}
