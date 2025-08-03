package fr.mediaskol.projet.dto.sessionDate;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationDistanciel;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.bo.sessionDate.SessionDate;
import fr.mediaskol.projet.bo.sessionDate.StatutSessionDate;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationResponseDTO;
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
public class SessionDateRespDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionDate;
    private LocalDate dateSession;
    private Integer duree;
    private LocalDateTime heureVisio;
    private StatutSessionDate statutSessionDate;
    private SessionFormateurRespDTO sessionFormateur;
    private SessionFormationResponseDTO sessionFormation;
    private SessionSalleRespDTO sessionSalle;

    /**
     * Constructeur
     * @param sessionDate
     */
    public SessionDateRespDTO(SessionDate sessionDate){
        this.idSessionDate = sessionDate.getIdSessionDate();
        this.dateSession = sessionDate.getDateSession();
        this.duree = sessionDate.getDuree();
        this.heureVisio = sessionDate.getHeureVisio();
        this.statutSessionDate = sessionDate.getStatutSessionDate();

        if(sessionDate.getSessionFormateur() != null){
            this.sessionFormateur = new  SessionFormateurRespDTO(sessionDate.getSessionFormateur());
        } else {
            this.sessionFormateur = null;
        }


        SessionFormation formation = sessionDate.getSessionFormation();

        if (formation instanceof SessionFormationPresentiel) {
            this.sessionFormation = new SessionFOPResponseDTO((SessionFormationPresentiel) formation);
        } else if (formation instanceof SessionFormationDistanciel) {
            this.sessionFormation = new SessionFOADResponseDTO((SessionFormationDistanciel) formation);
        } else {
            this.sessionFormation = null;
        }


        if(sessionDate.getSessionSalle() != null){
            this.sessionSalle = new  SessionSalleRespDTO(sessionDate.getSessionSalle());
        } else {
            this.sessionSalle = null;
        }
    }

}
