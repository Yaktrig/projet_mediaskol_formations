package fr.mediaskol.projet.dto.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormationDistanciel;
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

//    /**
//     * Déclaration des attributs
//     */
//    private Long idSessionFormation;
//    private String noYoda;
//    private String libelleSessionFormation;
//    private String statutYoda;
//    private LocalDate dateDebutSession;
//    private Integer nbHeureSession;
//    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;
//    private FormationResponseDTO formation;
//    private FinSessionFormationRespDTO finSessionFormation;
//    private SalarieResponseDTO salarie;

    private String contratSessionFormationDistanciel;
    private Integer nbBlocSessionFormationDistanciel;
    private LocalDate dateFinSessionFormationDistanciel;
    private LocalDate dateRelanceSessionFormationDistanciel;
    private String commentaireSessionFormationDistanciel;



    /**
     * Constructeur
     */
    public SessionFOADResponseDTO(SessionFormationDistanciel sessionFOAD){

//        this.idSessionFormation =  sessionFOAD.getIdSessionFormation();
//        this.noYoda = sessionFOAD.getNoYoda();
//        this.libelleSessionFormation = sessionFOAD.getLibelleSessionFormation();
//        this.statutYoda = sessionFOAD.getStatutYoda();
//        this.dateDebutSession = sessionFOAD.getDateDebutSession();
//        this.nbHeureSession = sessionFOAD.getNbHeureSession();
//        this.statutSessionFormation = sessionFOAD.getStatutSessionFormation();
//
//        if (sessionFOAD.getFormation() != null) {
//            this.formation = new FormationResponseDTO(sessionFOAD.getFormation());
//        } else {
//            this.formation = null;
//        }

//
//        if (sessionFOAD.getFinSessionFormation() != null) {
//            this.finSessionFormation = new FinSessionFormationRespDTO(sessionFOAD.getFinSessionFormation());
//        } else {
//            this.finSessionFormation = null;
//        }
//
//
//        if (sessionFOAD.getSalarie() != null) {
//            this.salarie = new SalarieResponseDTO(sessionFOAD.getSalarie());
//        } else {
//            this.salarie = null;
//        }

        this.contratSessionFormationDistanciel = sessionFOAD.getContratSessionFormationDistanciel();
        this.nbBlocSessionFormationDistanciel = sessionFOAD.getNbBlocSessionFormationDistanciel();
        this.dateFinSessionFormationDistanciel = sessionFOAD.getDateFinSessionFormationDistanciel();
        this.dateRelanceSessionFormationDistanciel = sessionFOAD.getDateRelanceSessionFormationDistanciel();
        this.commentaireSessionFormationDistanciel = sessionFOAD.getCommentaireSessionFormationDistanciel();
    }

}
