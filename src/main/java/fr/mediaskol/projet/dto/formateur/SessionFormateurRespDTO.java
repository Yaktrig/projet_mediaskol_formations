package fr.mediaskol.projet.dto.formateur;

import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.formateur.StatutSessionFormateur;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFormateurRespDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionFormateur;
    private String commentaireSessionFormateur;
    private StatutSessionFormateur statutSessionFormateur;
    private FormateurResponseDTO formateur;
    private SessionFormationRespDTO sessionFormation;

    /**
     * Constructeur
     */
    public SessionFormateurRespDTO(SessionFormateur sessionFormateur){
        this.idSessionFormateur = sessionFormateur.getIdSessionFormateur();
        this.commentaireSessionFormateur = sessionFormateur.getCommentaireSessionFormateur();
        this.formateur = new  FormateurResponseDTO(sessionFormateur.getFormateur());
        this.sessionFormation = new  SessionFormationRespDTO(sessionFormateur.getSessionFormation());
    }
}
