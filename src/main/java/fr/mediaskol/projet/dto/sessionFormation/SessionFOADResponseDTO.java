package fr.mediaskol.projet.dto.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder // Héritage joined
public class SessionFOADResponseDTO extends SessionFormationResponseDTO {


    private Long idSessionFormationDistanciel;
    private String noYoda;
    private String libelleSessionFormation;
    private String statutYoda;
    private LocalDate dateDebutSession;
    private Integer nbHeureSession;
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;
    private FormationResponseDTO formation;
    private FinSessionFormationRespDTO finSessionFormation;
    private SalarieResponseDTO salarie;

    private String contratSessionFormationDistanciel;
    private Integer nbBlocSessionFormationDistanciel;
    private LocalDate dateFinSessionFormationDistanciel;
    private LocalDate dateRelanceSessionFormationDistanciel;
    private String commentaireSessionFormationDistanciel;



    /**
     * Constructeur
     */
    public SessionFOADResponseDTO(SessionFormationDistanciel sessionFOAD){

        this.idSessionFormationDistanciel=  sessionFOAD.getIdSessionFormationDistanciel();
        this.contratSessionFormationDistanciel = sessionFOAD.getContratSessionFormationDistanciel();
        this.nbBlocSessionFormationDistanciel = sessionFOAD.getNbBlocSessionFormationDistanciel();
        this.dateDebutSession = sessionFOAD.getDateDebutSessionFormationDistanciel();
        this.dateFinSessionFormationDistanciel = sessionFOAD.getDateFinSessionFormationDistanciel();
        this.dateRelanceSessionFormationDistanciel = sessionFOAD.getDateRelanceSessionFormationDistanciel();
        this.commentaireSessionFormationDistanciel = sessionFOAD.getCommentaireSessionFormationDistanciel();
    }

}
