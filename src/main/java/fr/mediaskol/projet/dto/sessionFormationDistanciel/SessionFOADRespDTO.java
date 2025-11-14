package fr.mediaskol.projet.dto.sessionFormationDistanciel;


import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFOADRespDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionFormationDistanciel;
    private String contratSessionFormationDistanciel;
    private Integer nbBlocSessionFormationDistanciel;
    private LocalDate dateDebutSessionFormationDistanciel;
    private LocalDate dateFinSessionFormationDistanciel;
    private LocalDate dateRelanceSessionFormationDistanciel;
    private String commentaireSessionFormationDistanciel;



    /**
     * Constructeur
     */
    public SessionFOADRespDTO(SessionFormationDistanciel sessionFOAD){

        this.idSessionFormationDistanciel = sessionFOAD.getIdSessionFormationDistanciel();
        this.contratSessionFormationDistanciel = sessionFOAD.getContratSessionFormationDistanciel();
        this.dateDebutSessionFormationDistanciel = sessionFOAD.getDateDebutSessionFormationDistanciel();
        this.dateFinSessionFormationDistanciel = sessionFOAD.getDateFinSessionFormationDistanciel();
        this.dateRelanceSessionFormationDistanciel = sessionFOAD.getDateRelanceSessionFormationDistanciel();
        this.commentaireSessionFormationDistanciel = sessionFOAD.getCommentaireSessionFormationDistanciel();
    }

}
