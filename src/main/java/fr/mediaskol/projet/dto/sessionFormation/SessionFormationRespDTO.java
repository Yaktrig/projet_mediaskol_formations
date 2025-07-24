package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
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
    private String statutYoda = "DO";
    private LocalDate dateDebutSession;
    private Integer nbHeureSession;
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;
    private FormationResponseDTO formation;
    private DepartementDTO departement;
   // private FinSessionFormation finSessionFormation;
   // private SessionFormationDistanciel sessionFormationDistanciel;

    /**
     * Constructeur
     */
    public SessionFormationRespDTO(SessionFormation sessionFormation){

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



       // this.finSessionFormation = sessionFormation.getFinSessionFormation();
        //this.sessionFormationDistanciel = sessionFormation.getSessionFormationDistanciel();
    }

}
