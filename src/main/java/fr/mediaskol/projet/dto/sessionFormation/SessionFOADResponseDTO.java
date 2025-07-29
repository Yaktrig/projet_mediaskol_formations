package fr.mediaskol.projet.dto.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormationDistanciel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFOADResponseDTO {

    /**
     * Déclaration des attributs
     */

    private String contratSessionFormationDistanciel;
    private Integer nbBlocSessionFormationDistanciel;
    private LocalDate dateDebutSessionFormationDistanciel;
    private LocalDate dateFinSessionFormationDistanciel;
    private LocalDate dateRelanceSessionFormationDistanciel;
    private String commentaireSessionFormationDistanciel;



    /**
     * Constructeur
     */
    public SessionFOADResponseDTO(SessionFormationDistanciel sessionFOAD){

        this.contratSessionFormationDistanciel = sessionFOAD.getContratSessionFormationDistanciel();
        this.dateFinSessionFormationDistanciel = sessionFOAD.getDateFinSessionFormationDistanciel();
        this.dateRelanceSessionFormationDistanciel = sessionFOAD.getDateRelanceSessionFormationDistanciel();
        this.commentaireSessionFormationDistanciel = sessionFOAD.getCommentaireSessionFormationDistanciel();
    }

}
