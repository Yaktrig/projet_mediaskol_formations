package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFormationRespDTO {

    private Long idSessionFormation;
    private String noYoda;
    private String libelleSessionFormation;
    private String statutYoda;
    private LocalDate dateDebutSession;
    private Integer nbHeureSession;
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;
    private FormationResponseDTO formation;
    private FinSessionFormationRespDTO finSessionFormation;
    private SalarieResponseDTO salarie;


    /**
     * Constructeur
     */
    public SessionFormationRespDTO(SessionFormation sessionFormation){

        this.idSessionFormation =  sessionFormation.getIdSessionFormation();
        this.noYoda = sessionFormation.getNoYoda();
        this.libelleSessionFormation = sessionFormation.getLibelleSessionFormation();
        this.statutYoda = sessionFormation.getStatutYoda();
        this.dateDebutSession = sessionFormation.getDateDebutSession();
        this.nbHeureSession = sessionFormation.getNbHeureSession();
        this.statutSessionFormation = sessionFormation.getStatutSessionFormation();

        if (sessionFormation.getFormation() != null) {
            this.formation = new FormationResponseDTO(sessionFormation.getFormation());
        } else {
            this.formation = null;
        }


        if (sessionFormation.getFinSessionFormation() != null) {
            this.finSessionFormation = new FinSessionFormationRespDTO(sessionFormation.getFinSessionFormation());
        } else {
            this.finSessionFormation = null;
        }


        if (sessionFormation.getSalarie() != null) {
            this.salarie = new SalarieResponseDTO(sessionFormation.getSalarie());
        } else {
            this.salarie = null;
        }
    }


}
