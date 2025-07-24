package fr.mediaskol.projet.dto.salle;


import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.salle.StatutSessionSalle;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.dto.adresse.AdresseResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private SessionFormationRespDTO sessionFormation;




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

        if(sessionSalle.getSessionFormation() != null){
            this.sessionFormation = new SessionFormationRespDTO(sessionSalle.getSessionFormation());
        } else {
            this.sessionFormation = null;
        }

    }





}
