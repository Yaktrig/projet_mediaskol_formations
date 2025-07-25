package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.StatutFinSessionFormation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FinSessionFormationRespDTO {

    /**
     * Déclaration des attributs de fin de formation
     */
    private Long idFinSessionFormation;
    private String statutYodaFinSessionFormation;
    private LocalDate dateLimiteYodaFinSessionFormation;
    private String commentaireFinSessionFormation;
    private StatutFinSessionFormation statutFinSessionFormation;

    /**
     * Constructeur
     */
    public FinSessionFormationRespDTO(FinSessionFormation finSessionFormation) {
        this.idFinSessionFormation = finSessionFormation.getIdFinSessionFormation();
        this.statutYodaFinSessionFormation = finSessionFormation.getStatutYodaFinSessionFormation();
        this.dateLimiteYodaFinSessionFormation = finSessionFormation.getDateLimiteYodaFinSessionFormation();
        this.commentaireFinSessionFormation = finSessionFormation.getCommentaireFinSessionFormation();
        this.statutFinSessionFormation = finSessionFormation.getStatutFinSessionFormation();
    }
}
