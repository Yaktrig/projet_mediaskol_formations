package fr.mediaskol.projet.dto.salle;


import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.salle.StatutSessionSalle;
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
public class SessionSalleRespDTO {


    /**
     * Déclaration des attributs
     */
    private Long idSessionSalle;
    private String commentaireSessionSalle;
    private Float coutSessionSalle;
    private StatutSessionSalle statutSessionSalle;
    private SalleResponseDTO salle;
    private SessionFOPResponseDTO sessionFOP;




    /**
     * Constructeur
     * @param sessionSalle
     */
    public SessionSalleRespDTO(SessionSalle sessionSalle){

        this.idSessionSalle = sessionSalle.getIdSessionSalle();
        this.commentaireSessionSalle = sessionSalle.getCommentaireSessionSalle();
        this.coutSessionSalle = sessionSalle.getCoutSessionSalle();
        this.statutSessionSalle = sessionSalle.getStatutSessionSalle();

        if(sessionSalle.getSalle() != null){
            this.salle = new  SalleResponseDTO(sessionSalle.getSalle());
        } else {
            this.salle = null;
        }

        if(sessionSalle.getSessionFormationPresentiel() != null){
            this.sessionFOP = new SessionSalleRespDTO(sessionSalle.getSessionFormationPresentiel());
        } else {
            this.sessionFOP = null;
        }

    }





}
