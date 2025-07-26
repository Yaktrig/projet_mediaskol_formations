package fr.mediaskol.projet.dto.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.sessionFormationDistanciel.SessionFOADRespDTO;
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
    private DepartementDTO departement;
    private FinSessionFormationRespDTO finSessionFormation;
    private SessionFOADRespDTO sessionFormationDistanciel;

    /**
     * Constructeur
     */
    public SessionFormationRespDTO(SessionFormation sessionFormation) {

        this.idSessionFormation = sessionFormation.getIdSessionFormation();
        this.noYoda = sessionFormation.getNoYoda();
        this.libelleSessionFormation = sessionFormation.getLibelleSessionFormation();
        this.statutYoda = sessionFormation.getStatutYoda();
        this.dateDebutSession = sessionFormation.getDateDebutSession();
        this.nbHeureSession = sessionFormation.getNbHeureSession();
        this.statutSessionFormation = sessionFormation.getStatutSessionFormation();

        this.formation = new FormationResponseDTO(sessionFormation.getFormation());

        if (sessionFormation.getDepartement() != null) {
            this.departement = new DepartementDTO(sessionFormation.getDepartement());
        } else {
            this.departement = null;
        }

        if (sessionFormation.getSessionFormationDistanciel() != null) {
            this.sessionFormationDistanciel = new SessionFOADRespDTO(sessionFormation.getSessionFormationDistanciel());
        } else {
            this.sessionFormationDistanciel = null;
        }

        if (sessionFormation.getFinSessionFormation() != null) {
            this.finSessionFormation = new FinSessionFormationRespDTO(sessionFormation.getFinSessionFormation());
        } else {
            this.finSessionFormation = null;
        }
    }

}
