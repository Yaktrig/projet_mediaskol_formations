package fr.mediaskol.projet.dto.formateur;

import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.formateur.StatutSessionFormateur;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationResponseDTO;
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
    private Float coutSessionFormateur;
    private StatutSessionFormateur statutSessionFormateur;
    private FormateurResponseDTO formateur;
    private SessionFormationResponseDTO sessionFormation;

    /**
     * Constructeur
     */
    public SessionFormateurRespDTO(SessionFormateur sessionFormateur){
        this.idSessionFormateur = sessionFormateur.getIdSessionFormateur();
        this.commentaireSessionFormateur = sessionFormateur.getCommentaireSessionFormateur();
        this.coutSessionFormateur = sessionFormateur.getCoutSessionFormateur();
        this.statutSessionFormateur = sessionFormateur.getStatutSessionFormateur();
        this.formateur = new  FormateurResponseDTO(sessionFormateur.getFormateur());

        SessionFormation formation = sessionFormateur.getSessionFormation();

        if (formation instanceof SessionFormationPresentiel) {
            this.sessionFormation = new SessionFOPResponseDTO((SessionFormationPresentiel) formation);
        } else {
            this.sessionFormation = null;
        }
    }
}
