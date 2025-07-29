package fr.mediaskol.projet.dto.sessionLieuDate;


import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.bo.sessionLieuDate.StatutSessionLieuDate;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
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
public class SessionLieuDateRespDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionLieuDate;
    private LocalDate dateSession;
    private String lieuSession;
    private Integer duree;
    private LocalDateTime heureVisio;
    private StatutSessionLieuDate statutSessionLieuDate;
    private SessionFormateurRespDTO sessionFormateur;
    private SessionFormationRespDTO sessionFormation;
    private SessionSalleRespDTO sessionSalle;

    /**
     * Constructeur
     * @param sessionLieuDate
     */
    public SessionLieuDateRespDTO(SessionLieuDate sessionLieuDate){
        this.idSessionLieuDate = sessionLieuDate.getIdSessionLieuDate();
        this.dateSession = sessionLieuDate.getDateSession();
        this.lieuSession = sessionLieuDate.getLieuSession();
        this.duree = sessionLieuDate.getDuree();
        this.heureVisio = sessionLieuDate.getHeureVisio();
        this.statutSessionLieuDate = sessionLieuDate.getStatutSessionLieuDate();

        if(sessionLieuDate.getSessionFormateur() != null){
            this.sessionFormateur = new  SessionFormateurRespDTO(sessionLieuDate.getSessionFormateur());
        } else {
            this.sessionFormateur = null;
        }

        if(sessionLieuDate.getSessionFormation() != null){
            this.sessionFormation = new SessionFormationRespDTO(sessionLieuDate.getSessionFormation());
        } else {
            this.sessionFormation = null;
        }

        if(sessionLieuDate.getSessionSalle() != null){
            this.sessionSalle = new  SessionSalleRespDTO(sessionLieuDate.getSessionSalle());
        } else {
            this.sessionSalle = null;
        }
    }

}
